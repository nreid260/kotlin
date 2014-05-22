/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jet.lang.descriptors.DeclarationDescriptor;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;

public interface ClassBuilder {
    @NotNull
    FieldVisitor newField(
            @Nullable PsiElement origin,
            @Nullable DeclarationDescriptor descriptor,
            int access,
            @NotNull String name,
            @NotNull String desc,
            @Nullable String signature,
            @Nullable Object value
    );

    @NotNull
    MethodVisitor newMethod(
            @Nullable PsiElement origin,
            @Nullable DeclarationDescriptor descriptor,
            int access,
            @NotNull String name,
            @NotNull String desc,
            @Nullable String signature,
            @Nullable String[] exceptions
    );

    @NotNull
    JvmSerializationBindings getSerializationBindings();

    @NotNull
    AnnotationVisitor newAnnotation(@NotNull String desc, boolean visible);

    void done();

    @NotNull
    ClassVisitor getVisitor();

    void defineClass(
            @Nullable PsiElement origin,
            int version,
            int access,
            @NotNull String name,
            @Nullable String signature,
            @NotNull String superName,
            @NotNull String[] interfaces
    );

    void visitSource(@NotNull String name, @Nullable String debug);

    void visitOuterClass(@NotNull String owner, @Nullable String name, @Nullable String desc);

    void visitInnerClass(@NotNull String name, @Nullable String outerName, @Nullable String innerName, int access);

    @NotNull
    String getThisName();
}
