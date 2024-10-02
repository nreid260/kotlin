/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.common.lower

import org.jetbrains.kotlin.backend.common.BodyLoweringPass
import org.jetbrains.kotlin.backend.common.CommonBackendContext
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.ir.asInlinable
import org.jetbrains.kotlin.backend.common.ir.inline
import org.jetbrains.kotlin.backend.common.phaser.PhaseDescription
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol
import org.jetbrains.kotlin.ir.types.*
import org.jetbrains.kotlin.ir.util.*
import org.jetbrains.kotlin.ir.visitors.transformChildrenVoid
import org.jetbrains.kotlin.util.OperatorNameConventions

/**
 * Transforms `Array(size) { index -> value }` into a loop.
 */
@PhaseDescription(name = "ArrayConstructor")
class ArrayConstructorLowering(val context: CommonBackendContext) : BodyLoweringPass {
    override fun lower(irBody: IrBody, container: IrDeclaration) {
        irBody.transformChildrenVoid(ArrayConstructorTransformer(context, container as IrSymbolOwner))
    }
}

private class ArrayConstructorTransformer(
    val context: CommonBackendContext,
    val container: IrSymbolOwner
) : IrElementTransformerVoidWithContext() {

    // Array(size, init) -> Array(size)
    companion object {
        internal fun arrayInlineToSizeConstructor(context: CommonBackendContext, irConstructor: IrConstructor): IrFunctionSymbol? {
            val clazz = irConstructor.constructedClass.symbol
            return when {
                irConstructor.valueParameters.size != 2 -> null
                clazz == context.irBuiltIns.arrayClass -> context.ir.symbols.arrayOfNulls // Array<T> has no unary constructor: it can only exist for Array<T?>
                context.irBuiltIns.primitiveArraysToPrimitiveTypes.contains(clazz) -> clazz.constructors.single {
                    val valueParameters = it.owner.valueParameters
                    valueParameters.size == 1 && valueParameters.first().type.isInt()
                }
                else -> null
            }
        }
    }

    override fun visitConstructorCall(expression: IrConstructorCall): IrExpression {
        val sizeConstructor = arrayInlineToSizeConstructor(context, expression.symbol.owner)
            ?: return super.visitConstructorCall(expression)
        // inline fun <reified T> Array(size: Int, invokable: (Int) -> T): Array<T> {
        //     val result = arrayOfNulls<T>(size)
        //     for (i in 0 until size) {
        //         result[i] = invokable(i)
        //     }
        //     return result as Array<T>
        // }
        // (and similar for primitive arrays)
        val size = expression.getValueArgument(0)!!.transform(this, null)
        val invokable = expression.getValueArgument(1)!!.transform(this, null)
        if (invokable.type.isNothing()) {
            // Expressions of type 'Nothing' don't terminate.
            return invokable
        }
        val scope = (currentScope ?: createScope(container)).scope
        return context.createIrBuilder(scope.scopeOwnerSymbol).irBlock(expression.startOffset, expression.endOffset) {
            val index = createTmpVariable(irInt(0), isMutable = true)
            val sizeVar = createTmpVariable(size)
            val result = createTmpVariable(irCall(sizeConstructor, expression.type).apply {
                copyTypeArgumentsFrom(expression)
                putValueArgument(0, irGet(sizeVar))
            })

            val generator = invokable.asInlinable(this)
            +irWhile().apply {
                condition = irCall(context.irBuiltIns.lessFunByOperandType[index.type.classifierOrFail]!!).apply {
                    putValueArgument(0, irGet(index))
                    putValueArgument(1, irGet(sizeVar))
                }
                body = irBlock {
                    val tempIndex = createTmpVariable(irGet(index))
                    +irCall(result.type.getClass()!!.functions.single { it.name == OperatorNameConventions.SET }).apply {
                        dispatchReceiver = irGet(result)
                        putValueArgument(0, irGet(tempIndex))
                        val inlined = generator.inline(parent, listOf(tempIndex))
                        putValueArgument(1, inlined)
                    }
                    val inc = index.type.getClass()!!.functions.single { it.name == OperatorNameConventions.INC }
                    +irSet(
                        index.symbol,
                        irCallOp(inc.symbol, index.type, irGet(index)),
                        origin = IrStatementOrigin.PREFIX_INCR
                    )
                }
            }
            +irGet(result)
        }
    }
}
