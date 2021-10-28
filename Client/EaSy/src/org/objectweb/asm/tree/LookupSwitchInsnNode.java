/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.util.Collection
 *  java.util.List
 *  java.util.Map
 */
package org.objectweb.asm.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.Util;

public class LookupSwitchInsnNode
extends AbstractInsnNode {
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;

    public LookupSwitchInsnNode(LabelNode dflt, int[] keys, LabelNode[] labels) {
        super(171);
        this.dflt = dflt;
        this.keys = Util.asArrayList(keys);
        this.labels = Util.asArrayList(labels);
    }

    @Override
    public int getType() {
        return 12;
    }

    @Override
    public void accept(MethodVisitor methodVisitor) {
        int[] keysArray = new int[this.keys.size()];
        int n = keysArray.length;
        for (int i = 0; i < n; ++i) {
            keysArray[i] = (Integer)this.keys.get(i);
        }
        Label[] labelsArray = new Label[this.labels.size()];
        int n2 = labelsArray.length;
        for (int i = 0; i < n2; ++i) {
            labelsArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
        }
        methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), keysArray, labelsArray);
        this.acceptAnnotations(methodVisitor);
    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        LookupSwitchInsnNode clone = new LookupSwitchInsnNode(LookupSwitchInsnNode.clone(this.dflt, clonedLabels), null, LookupSwitchInsnNode.clone(this.labels, clonedLabels));
        clone.keys.addAll(this.keys);
        return clone.cloneAnnotations(this);
    }
}

