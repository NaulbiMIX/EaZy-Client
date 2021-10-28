/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.TypeAnnotationNode;

public class TryCatchBlockNode {
    public LabelNode start;
    public LabelNode end;
    public LabelNode handler;
    public String type;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;

    public TryCatchBlockNode(LabelNode start, LabelNode end, LabelNode handler, String type) {
        this.start = start;
        this.end = end;
        this.handler = handler;
        this.type = type;
    }

    public void updateIndex(int index) {
        int n;
        int i;
        int newTypeRef = 1107296256 | index << 8;
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                ((TypeAnnotationNode)this.visibleTypeAnnotations.get((int)i)).typeRef = newTypeRef;
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                ((TypeAnnotationNode)this.invisibleTypeAnnotations.get((int)i)).typeRef = newTypeRef;
            }
        }
    }

    public void accept(MethodVisitor methodVisitor) {
        TypeAnnotationNode typeAnnotation;
        int n;
        int i;
        methodVisitor.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), this.handler == null ? null : this.handler.getLabel(), this.type);
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitTryCatchAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
    }
}

