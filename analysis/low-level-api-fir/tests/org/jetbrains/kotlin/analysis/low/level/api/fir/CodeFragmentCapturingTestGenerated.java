/*
 * Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.low.level.api.fir;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing")
@TestDataPath("$PROJECT_ROOT")
public class CodeFragmentCapturingTestGenerated extends AbstractCodeFragmentCapturingTest {
  @Test
  public void testAllFilesPresentInCapturing() {
    KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing"), Pattern.compile("^([^.]+)\\.kt$"), null, true);
  }

  @Test
  @TestMetadata("contextReceiver.kt")
  public void testContextReceiver() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/contextReceiver.kt");
  }

  @Test
  @TestMetadata("contextReceiverExplicit.kt")
  public void testContextReceiverExplicit() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/contextReceiverExplicit.kt");
  }

  @Test
  @TestMetadata("extensionReceiver.kt")
  public void testExtensionReceiver() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiver.kt");
  }

  @Test
  @TestMetadata("extensionReceiverExplicit.kt")
  public void testExtensionReceiverExplicit() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverExplicit.kt");
  }

  @Test
  @TestMetadata("extensionReceiverLabeled.kt")
  public void testExtensionReceiverLabeled() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverLabeled.kt");
  }

  @Test
  @TestMetadata("extensionReceiverProperty.kt")
  public void testExtensionReceiverProperty() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverProperty.kt");
  }

  @Test
  @TestMetadata("extensionReceiverPropertyExplicit.kt")
  public void testExtensionReceiverPropertyExplicit() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverPropertyExplicit.kt");
  }

  @Test
  @TestMetadata("extensionReceiverPropertyLabeled.kt")
  public void testExtensionReceiverPropertyLabeled() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverPropertyLabeled.kt");
  }

  @Test
  @TestMetadata("extensionReceiverPropertyPrivate.kt")
  public void testExtensionReceiverPropertyPrivate() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverPropertyPrivate.kt");
  }

  @Test
  @TestMetadata("extensionReceiverSmartCasted.kt")
  public void testExtensionReceiverSmartCasted() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/extensionReceiverSmartCasted.kt");
  }

  @Test
  @TestMetadata("foreignValue.kt")
  public void testForeignValue() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/foreignValue.kt");
  }

  @Test
  @TestMetadata("initializer.kt")
  public void testInitializer() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/initializer.kt");
  }

  @Test
  @TestMetadata("lambda1.kt")
  public void testLambda1() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/lambda1.kt");
  }

  @Test
  @TestMetadata("lambda2.kt")
  public void testLambda2() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/lambda2.kt");
  }

  @Test
  @TestMetadata("lambda3.kt")
  public void testLambda3() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/lambda3.kt");
  }

  @Test
  @TestMetadata("local.kt")
  public void testLocal() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/local.kt");
  }

  @Test
  @TestMetadata("localDelegatedProperty.kt")
  public void testLocalDelegatedProperty() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localDelegatedProperty.kt");
  }

  @Test
  @TestMetadata("localFunction.kt")
  public void testLocalFunction() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunction.kt");
  }

  @Test
  @TestMetadata("localFunctionContainingClassClosure.kt")
  public void testLocalFunctionContainingClassClosure() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionContainingClassClosure.kt");
  }

  @Test
  @TestMetadata("localFunctionExtensionReceiverClosure.kt")
  public void testLocalFunctionExtensionReceiverClosure() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionExtensionReceiverClosure.kt");
  }

  @Test
  @TestMetadata("localFunctionLambdaParameterClosure.kt")
  public void testLocalFunctionLambdaParameterClosure() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionLambdaParameterClosure.kt");
  }

  @Test
  @TestMetadata("localFunctionLocalClosure.kt")
  public void testLocalFunctionLocalClosure() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionLocalClosure.kt");
  }

  @Test
  @TestMetadata("localFunctionLocalClosureMutating.kt")
  public void testLocalFunctionLocalClosureMutating() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionLocalClosureMutating.kt");
  }

  @Test
  @TestMetadata("localFunctionMultipleCapturing.kt")
  public void testLocalFunctionMultipleCapturing() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionMultipleCapturing.kt");
  }

  @Test
  @TestMetadata("localFunctionParameterClosure.kt")
  public void testLocalFunctionParameterClosure() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localFunctionParameterClosure.kt");
  }

  @Test
  @TestMetadata("localMutated.kt")
  public void testLocalMutated() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localMutated.kt");
  }

  @Test
  @TestMetadata("localMutatedWithAugmentedAssignment.kt")
  public void testLocalMutatedWithAugmentedAssignment() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localMutatedWithAugmentedAssignment.kt");
  }

  @Test
  @TestMetadata("localMutatedWithSugar.kt")
  public void testLocalMutatedWithSugar() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localMutatedWithSugar.kt");
  }

  @Test
  @TestMetadata("localVariable.kt")
  public void testLocalVariable() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/localVariable.kt");
  }

  @Test
  @TestMetadata("multipleClassAndFunctionContextReceivers.kt")
  public void testMultipleClassAndFunctionContextReceivers() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/multipleClassAndFunctionContextReceivers.kt");
  }

  @Test
  @TestMetadata("multipleClassContextReceivers.kt")
  public void testMultipleClassContextReceivers() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/multipleClassContextReceivers.kt");
  }

  @Test
  @TestMetadata("multipleFunctionContextReceivers.kt")
  public void testMultipleFunctionContextReceivers() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/multipleFunctionContextReceivers.kt");
  }

  @Test
  @TestMetadata("nestedOuterClass.kt")
  public void testNestedOuterClass() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/nestedOuterClass.kt");
  }

  @Test
  @TestMetadata("objectFunction.kt")
  public void testObjectFunction() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/objectFunction.kt");
  }

  @Test
  @TestMetadata("outerClass.kt")
  public void testOuterClass() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/outerClass.kt");
  }

  @Test
  @TestMetadata("outerClassMutated.kt")
  public void testOuterClassMutated() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/outerClassMutated.kt");
  }

  @Test
  @TestMetadata("outerClassMutatedPrivate.kt")
  public void testOuterClassMutatedPrivate() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/outerClassMutatedPrivate.kt");
  }

  @Test
  @TestMetadata("preferCorrectResolve.kt")
  public void testPreferCorrectResolve() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/preferCorrectResolve.kt");
  }

  @Test
  @TestMetadata("privateMemberCall.kt")
  public void testPrivateMemberCall() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/privateMemberCall.kt");
  }

  @Test
  @TestMetadata("secondClassContextReceiver.kt")
  public void testSecondClassContextReceiver() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/secondClassContextReceiver.kt");
  }

  @Test
  @TestMetadata("singleClassContextReceiver.kt")
  public void testSingleClassContextReceiver() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/singleClassContextReceiver.kt");
  }

  @Test
  @TestMetadata("smartCastExtensionReceiver.kt")
  public void testSmartCastExtensionReceiver() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/smartCastExtensionReceiver.kt");
  }

  @Test
  @TestMetadata("smartCastValueParameter.kt")
  public void testSmartCastValueParameter() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/smartCastValueParameter.kt");
  }

  @Test
  @TestMetadata("valueParameter.kt")
  public void testValueParameter() {
    runTest("analysis/analysis-api/testData/components/compilerFacility/compilation/codeFragments/capturing/valueParameter.kt");
  }
}
