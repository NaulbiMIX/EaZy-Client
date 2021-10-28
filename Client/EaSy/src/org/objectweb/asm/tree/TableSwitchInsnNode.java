/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.List
 *  java.util.Map
 */
package org.objectweb.asm.tree;

import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.Util;

public class TableSwitchInsnNode
extends AbstractInsnNode {
    public int min;
    public int max;
    public LabelNode dflt;
    public List<LabelNode> labels;

    public /* varargs */ TableSwitchInsnNode(int min, int max, LabelNode dflt, LabelNode ... labels) {
        super(170);
        this.min = min;
        this.max = max;
        this.dflt = dflt;
        this.labels = Util.asArrayList(labels);
    }

    @Override
    public int getType() {
        return 11;
    }

    @Override
    public void accept(MethodVisitor methodVisitor) {
        Label[] labelsArray = new Label[this.labels.size()];
        int n = labelsArray.length;
        for (int i = 0; i < n; ++i) {
            labelsArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
        }
        methodVisitor.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), labelsArray);
        this.acceptAnnotations(methodVisitor);
    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        return new TableSwitchInsnNode(this.min, this.max, TableSwitchInsnNode.clone(this.dflt, clonedLabels), TableSwitchInsnNode.clone(this.labels, clonedLabels)).cloneAnnotations(this);
    }
}

