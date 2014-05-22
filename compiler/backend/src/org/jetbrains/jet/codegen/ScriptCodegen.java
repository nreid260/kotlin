/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.codegen;

import kotlin.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jet.codegen.context.CodegenContext;
import org.jetbrains.jet.codegen.context.MethodContext;
import org.jetbrains.jet.codegen.context.ScriptContext;
import org.jetbrains.jet.codegen.state.GenerationState;
import org.jetbrains.jet.lang.descriptors.ClassDescriptor;
import org.jetbrains.jet.lang.descriptors.PropertyDescriptor;
import org.jetbrains.jet.lang.descriptors.ScriptDescriptor;
import org.jetbrains.jet.lang.descriptors.ValueParameterDescriptor;
import org.jetbrains.jet.lang.psi.JetDeclaration;
import org.jetbrains.jet.lang.psi.JetScript;
import org.jetbrains.jet.lang.resolve.BindingContext;
import org.jetbrains.jet.lang.resolve.java.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

import java.util.Collections;
import java.util.List;

import static org.jetbrains.jet.codegen.binding.CodegenBinding.*;
import static org.jetbrains.jet.lang.resolve.java.AsmTypeConstants.OBJECT_TYPE;
import static org.jetbrains.org.objectweb.asm.Opcodes.*;

// SCRIPT: script code generator
public class ScriptCodegen extends MemberCodegen<JetScript> {

    public static ScriptCodegen createScriptCodegen(
            @NotNull JetScript declaration,
            @NotNull GenerationState state,
            @NotNull CodegenContext parentContext
    ) {
        ScriptDescriptor scriptDescriptor = state.getBindingContext().get(BindingContext.SCRIPT, declaration);
        assert scriptDescriptor != null;

        ClassDescriptor classDescriptorForScript = state.getBindingContext().get(CLASS_FOR_SCRIPT, scriptDescriptor);
        assert classDescriptorForScript != null;

        Type className = state.getBindingContext().get(ASM_TYPE, classDescriptorForScript);
        assert className != null;

        ClassBuilder builder = state.getFactory().newVisitor(className, declaration.getContainingFile());
        List<ScriptDescriptor> earlierScripts = state.getEarlierScriptsForReplInterpreter();
        ScriptContext scriptContext = parentContext.intoScript(
                scriptDescriptor,
                earlierScripts == null ? Collections.<ScriptDescriptor>emptyList() : earlierScripts,
                classDescriptorForScript
        );
        return new ScriptCodegen(declaration, state, scriptContext, builder);
    }

    private final JetScript scriptDeclaration;
    private final ScriptContext context;
    private final ScriptDescriptor scriptDescriptor;

    private ScriptCodegen(
            @NotNull JetScript scriptDeclaration,
            @NotNull GenerationState state,
            @NotNull ScriptContext context,
            @NotNull ClassBuilder builder
    ) {
        super(state, null, context, scriptDeclaration, builder);
        this.scriptDeclaration = scriptDeclaration;
        this.context = context;
        this.scriptDescriptor = context.getScriptDescriptor();
    }

    @Override
    protected void generateDeclaration() {
        Type classType = bindingContext.get(ASM_TYPE, context.getContextDescriptor());
        assert classType != null;

        v.defineClass(scriptDeclaration,
                      V1_6,
                      ACC_PUBLIC,
                      classType.getInternalName(),
                      null,
                      "java/lang/Object",
                      new String[0]);
    }

    @Override
    protected void generateBody() {
        genMembers();
        genFieldsForParameters(scriptDescriptor, v);
        genConstructor(scriptDescriptor, context.getContextDescriptor(), v,
                       context.intoFunction(scriptDescriptor.getScriptCodeDescriptor()));
    }

    @Override
    protected void generateKotlinAnnotation() {
        // TODO
    }

    private void genConstructor(
            @NotNull ScriptDescriptor scriptDescriptor,
            @NotNull ClassDescriptor classDescriptorForScript,
            @NotNull ClassBuilder classBuilder,
            @NotNull final MethodContext methodContext
    ) {
        Type blockType = typeMapper.mapType(scriptDescriptor.getScriptCodeDescriptor().getReturnType());

        PropertyDescriptor scriptResultProperty = scriptDescriptor.getScriptResultProperty();
        classBuilder.newField(null, scriptResultProperty,
                              ACC_PUBLIC | ACC_FINAL, scriptResultProperty.getName().asString(),
                              blockType.getDescriptor(), null, null);

        JvmMethodSignature jvmSignature = typeMapper.mapScriptSignature(scriptDescriptor, context.getEarlierScripts());

        MethodVisitor mv = classBuilder.newMethod(
                scriptDeclaration, scriptDescriptor.getClassDescriptor().getUnsubstitutedPrimaryConstructor(),
                ACC_PUBLIC, jvmSignature.getAsmMethod().getName(), jvmSignature.getAsmMethod().getDescriptor(),
                null, null);

        mv.visitCode();

        final InstructionAdapter instructionAdapter = new InstructionAdapter(mv);

        Type classType = bindingContext.get(ASM_TYPE, classDescriptorForScript);
        assert classType != null;

        instructionAdapter.load(0, classType);
        instructionAdapter.invokespecial("java/lang/Object", "<init>", "()V");

        instructionAdapter.load(0, classType);

        final FrameMap frameMap = methodContext.prepareFrame(typeMapper);

        for (ScriptDescriptor importedScript : context.getEarlierScripts()) {
            frameMap.enter(importedScript, OBJECT_TYPE);
        }

        Type[] argTypes = jvmSignature.getAsmMethod().getArgumentTypes();
        int add = 0;

        for (int i = 0; i < scriptDescriptor.getScriptCodeDescriptor().getValueParameters().size(); i++) {
            ValueParameterDescriptor parameter = scriptDescriptor.getScriptCodeDescriptor().getValueParameters().get(i);
            frameMap.enter(parameter, argTypes[i + add]);
        }

        generateInitializers(new Function0<ExpressionCodegen>() {
            @Override
            public ExpressionCodegen invoke() {
                return new ExpressionCodegen(instructionAdapter, frameMap, Type.VOID_TYPE, methodContext, state, ScriptCodegen.this);
            }
        });

        int offset = 1;

        for (ScriptDescriptor earlierScript : context.getEarlierScripts()) {
            Type earlierClassType = asmTypeForScriptDescriptor(bindingContext, earlierScript);
            instructionAdapter.load(0, classType);
            instructionAdapter.load(offset, earlierClassType);
            offset += earlierClassType.getSize();
            instructionAdapter.putfield(classType.getInternalName(), context.getScriptFieldName(earlierScript), earlierClassType.getDescriptor());
        }

        for (ValueParameterDescriptor parameter : scriptDescriptor.getScriptCodeDescriptor().getValueParameters()) {
            Type parameterType = typeMapper.mapType(parameter.getType());
            instructionAdapter.load(0, classType);
            instructionAdapter.load(offset, parameterType);
            offset += parameterType.getSize();
            instructionAdapter.putfield(classType.getInternalName(), parameter.getName().getIdentifier(), parameterType.getDescriptor());
        }

        StackValue stackValue =
                new ExpressionCodegen(mv, frameMap, Type.VOID_TYPE, methodContext, state, this).gen(scriptDeclaration.getBlockExpression());
        if (stackValue.type != Type.VOID_TYPE) {
            stackValue.put(stackValue.type, instructionAdapter);
            instructionAdapter.putfield(classType.getInternalName(), ScriptDescriptor.LAST_EXPRESSION_VALUE_FIELD_NAME,
                                        blockType.getDescriptor());
        }

        instructionAdapter.areturn(Type.VOID_TYPE);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private void genFieldsForParameters(@NotNull ScriptDescriptor script, @NotNull ClassBuilder classBuilder) {
        for (ScriptDescriptor earlierScript : context.getEarlierScripts()) {
            Type earlierClassName = asmTypeForScriptDescriptor(bindingContext, earlierScript);
            int access = ACC_PRIVATE | ACC_FINAL;
            classBuilder.newField(null, null, access, context.getScriptFieldName(earlierScript), earlierClassName.getDescriptor(), null, null);
        }

        for (ValueParameterDescriptor parameter : script.getScriptCodeDescriptor().getValueParameters()) {
            Type parameterType = typeMapper.mapType(parameter);
            int access = ACC_PUBLIC | ACC_FINAL;
            classBuilder.newField(null, parameter, access, parameter.getName().getIdentifier(), parameterType.getDescriptor(), null, null);
        }
    }

    private void genMembers() {
        for (JetDeclaration declaration : scriptDeclaration.getDeclarations()) {
            genFunctionOrProperty(declaration);
        }
    }
}
