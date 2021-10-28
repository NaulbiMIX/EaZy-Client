/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Map
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.Util;

public class FrameNode
extends AbstractInsnNode {
    public int type;
    public List<Object> local;
    public List<Object> stack;

    private FrameNode() {
        super(-1);
    }

    public FrameNode(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        super(-1);
        this.type = type;
        switch (type) {
            case -1: 
            case 0: {
                this.local = Util.asArrayList(numLocal, local);
                this.stack = Util.asArrayList(numStack, stack);
            }
            case 1: {
                this.local = Util.asArrayList(numLocal, local);
            }
            case 2: {
                this.local = Util.asArrayList(numLocal);
            }
            case 3: {
                return;
            }
            case 4: {
                this.stack = Util.asArrayList(1, stack);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getType() {
        return 14;
    }

    @Override
    public void accept(MethodVisitor methodVisitor) {
        switch (this.type) {
            case -1: 
            case 0: {
                methodVisitor.visitFrame(this.type, this.local.size(), FrameNode.asArray(this.local), this.stack.size(), FrameNode.asArray(this.stack));
                return;
            }
            case 1: {
                methodVisitor.visitFrame(this.type, this.local.size(), FrameNode.asArray(this.local), 0, null);
                return;
            }
            case 2: {
                methodVisitor.visitFrame(this.type, this.local.size(), null, 0, null);
                return;
            }
            case 3: {
                methodVisitor.visitFrame(this.type, 0, null, 0, null);
                return;
            }
            case 4: {
                methodVisitor.visitFrame(this.type, 0, null, 1, FrameNode.asArray(this.stack));
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        int n;
        int i;
        FrameNode clone = new FrameNode();
        clone.type = this.type;
        if (this.local != null) {
            clone.local = new ArrayList();
            n = this.local.size();
            for (i = 0; i < n; ++i) {
                Object localElement = this.local.get(i);
                if (localElement instanceof LabelNode) {
                    localElement = clonedLabels.get(localElement);
                }
                clone.local.add(localElement);
            }
        }
        if (this.stack != null) {
            clone.stack = new ArrayList();
            n = this.stack.size();
            for (i = 0; i < n; ++i) {
                Object stackElement = this.stack.get(i);
                if (stackElement instanceof LabelNode) {
                    stackElement = clonedLabels.get(stackElement);
                }
                clone.stack.add(stackElement);
            }
        }
        return clone;
    }

    private static Object[] asArray(List<Object> list) {
        Object[] array = new Object[list.size()];
        int n = array.length;
        for (int i = 0; i < n; ++i) {
            Object o = list.get(i);
            if (o instanceof LabelNode) {
                o = ((LabelNode)o).getLabel();
            }
            array[i] = o;
        }
        return array;
    }
}

