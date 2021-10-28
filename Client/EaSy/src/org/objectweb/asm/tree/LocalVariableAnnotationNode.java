/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
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
import org.objectweb.asm.tree.Util;

public class LocalVariableAnnotationNode
extends TypeAnnotationNode {
    public List<LabelNode> start;
    public List<LabelNode> end;
    public List<Integer> index;

    public LocalVariableAnnotationNode(int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
        this(458752, typeRef, typePath, start, end, index, descriptor);
    }

    public LocalVariableAnnotationNode(int api, int typeRef, TypePath typePath, LabelNode[] start, LabelNode[] end, int[] index, String descriptor) {
        super(api, typeRef, typePath, descriptor);
        this.start = Util.asArrayList(start);
        this.end = Util.asArrayList(end);
        this.index = Util.asArrayList(index);
    }

    public void accept(MethodVisitor methodVisitor, boolean visible) {
        Label[] startLabels = new Label[this.start.size()];
        Label[] endLabels = new Label[this.end.size()];
        int[] indices = new int[this.index.size()];
        int n = startLabels.length;
        for (int i = 0; i < n; ++i) {
            startLabels[i] = ((LabelNode)this.start.get(i)).getLabel();
            endLabels[i] = ((LabelNode)this.end.get(i)).getLabel();
            indices[i] = (Integer)this.index.get(i);
        }
        this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, startLabels, endLabels, indices, this.desc, visible));
    }
}

