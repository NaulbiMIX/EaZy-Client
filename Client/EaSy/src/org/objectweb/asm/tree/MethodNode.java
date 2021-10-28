/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package org.objectweb.asm.tree;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableAnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.ParameterNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeAnnotationNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.UnsupportedClassVersionException;
import org.objectweb.asm.tree.Util;
import org.objectweb.asm.tree.VarInsnNode;

public class MethodNode
extends MethodVisitor {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public List<String> exceptions;
    public List<ParameterNode> parameters;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public Object annotationDefault;
    public int visibleAnnotableParameterCount;
    public List<AnnotationNode>[] visibleParameterAnnotations;
    public int invisibleAnnotableParameterCount;
    public List<AnnotationNode>[] invisibleParameterAnnotations;
    public InsnList instructions;
    public List<TryCatchBlockNode> tryCatchBlocks;
    public int maxStack;
    public int maxLocals;
    public List<LocalVariableNode> localVariables;
    public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
    public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
    private boolean visited;

    public MethodNode() {
        this(458752);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }

    public MethodNode(int api) {
        super(api);
        this.instructions = new InsnList();
    }

    public MethodNode(int access, String name, String descriptor, String signature, String[] exceptions) {
        this(458752, access, name, descriptor, signature, exceptions);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }

    public MethodNode(int api, int access, String name, String descriptor, String signature, String[] exceptions) {
        super(api);
        this.access = access;
        this.name = name;
        this.desc = descriptor;
        this.signature = signature;
        this.exceptions = Util.asArrayList(exceptions);
        if ((access & 1024) == 0) {
            this.localVariables = new ArrayList(5);
        }
        this.tryCatchBlocks = new ArrayList();
        this.instructions = new InsnList();
    }

    @Override
    public void visitParameter(String name, int access) {
        if (this.parameters == null) {
            this.parameters = new ArrayList(5);
        }
        this.parameters.add((Object)new ParameterNode(name, access));
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return new AnnotationNode((List<Object>)new ArrayList<Object>(0){

            public boolean add(Object o) {
                MethodNode.this.annotationDefault = o;
                return super.add(o);
            }
        });
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationNode annotation = new AnnotationNode(descriptor);
        if (visible) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList(1);
            }
            this.visibleAnnotations.add((Object)annotation);
        } else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList(1);
            }
            this.invisibleAnnotations.add((Object)annotation);
        }
        return annotation;
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
        if (visible) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList(1);
            }
            this.visibleTypeAnnotations.add((Object)typeAnnotation);
        } else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList(1);
            }
            this.invisibleTypeAnnotations.add((Object)typeAnnotation);
        }
        return typeAnnotation;
    }

    @Override
    public void visitAnnotableParameterCount(int parameterCount, boolean visible) {
        if (visible) {
            this.visibleAnnotableParameterCount = parameterCount;
        } else {
            this.invisibleAnnotableParameterCount = parameterCount;
        }
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        AnnotationNode annotation = new AnnotationNode(descriptor);
        if (visible) {
            if (this.visibleParameterAnnotations == null) {
                int params = Type.getArgumentTypes(this.desc).length;
                this.visibleParameterAnnotations = new List[params];
            }
            if (this.visibleParameterAnnotations[parameter] == null) {
                this.visibleParameterAnnotations[parameter] = new ArrayList(1);
            }
            this.visibleParameterAnnotations[parameter].add((Object)annotation);
        } else {
            if (this.invisibleParameterAnnotations == null) {
                int params = Type.getArgumentTypes(this.desc).length;
                this.invisibleParameterAnnotations = new List[params];
            }
            if (this.invisibleParameterAnnotations[parameter] == null) {
                this.invisibleParameterAnnotations[parameter] = new ArrayList(1);
            }
            this.invisibleParameterAnnotations[parameter].add((Object)annotation);
        }
        return annotation;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add((Object)attribute);
    }

    @Override
    public void visitCode() {
    }

    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        this.instructions.add(new FrameNode(type, numLocal, local == null ? null : this.getLabelNodes(local), numStack, stack == null ? null : this.getLabelNodes(stack)));
    }

    @Override
    public void visitInsn(int opcode) {
        this.instructions.add(new InsnNode(opcode));
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        this.instructions.add(new IntInsnNode(opcode, operand));
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        this.instructions.add(new VarInsnNode(opcode, var));
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        this.instructions.add(new TypeInsnNode(opcode, type));
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        this.instructions.add(new FieldInsnNode(opcode, owner, name, descriptor));
    }

    @Deprecated
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor) {
        if (this.api >= 327680) {
            super.visitMethodInsn(opcode, owner, name, descriptor);
            return;
        }
        this.instructions.add(new MethodInsnNode(opcode, owner, name, descriptor));
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (this.api < 327680) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            return;
        }
        this.instructions.add(new MethodInsnNode(opcode, owner, name, descriptor, isInterface));
    }

    @Override
    public /* varargs */ void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object ... bootstrapMethodArguments) {
        this.instructions.add(new InvokeDynamicInsnNode(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments));
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        this.instructions.add(new JumpInsnNode(opcode, this.getLabelNode(label)));
    }

    @Override
    public void visitLabel(Label label) {
        this.instructions.add(this.getLabelNode(label));
    }

    @Override
    public void visitLdcInsn(Object value) {
        this.instructions.add(new LdcInsnNode(value));
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        this.instructions.add(new IincInsnNode(var, increment));
    }

    @Override
    public /* varargs */ void visitTableSwitchInsn(int min, int max, Label dflt, Label ... labels) {
        this.instructions.add(new TableSwitchInsnNode(min, max, this.getLabelNode(dflt), this.getLabelNodes(labels)));
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        this.instructions.add(new LookupSwitchInsnNode(this.getLabelNode(dflt), keys, this.getLabelNodes(labels)));
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        this.instructions.add(new MultiANewArrayInsnNode(descriptor, numDimensions));
    }

    @Override
    public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AbstractInsnNode currentInsn = this.instructions.getLast();
        while (currentInsn.getOpcode() == -1) {
            currentInsn = currentInsn.getPrevious();
        }
        TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
        if (visible) {
            if (currentInsn.visibleTypeAnnotations == null) {
                currentInsn.visibleTypeAnnotations = new ArrayList(1);
            }
            currentInsn.visibleTypeAnnotations.add((Object)typeAnnotation);
        } else {
            if (currentInsn.invisibleTypeAnnotations == null) {
                currentInsn.invisibleTypeAnnotations = new ArrayList(1);
            }
            currentInsn.invisibleTypeAnnotations.add((Object)typeAnnotation);
        }
        return typeAnnotation;
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        this.tryCatchBlocks.add((Object)new TryCatchBlockNode(this.getLabelNode(start), this.getLabelNode(end), this.getLabelNode(handler), type));
    }

    @Override
    public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        TryCatchBlockNode tryCatchBlock = (TryCatchBlockNode)this.tryCatchBlocks.get((typeRef & 16776960) >> 8);
        TypeAnnotationNode typeAnnotation = new TypeAnnotationNode(typeRef, typePath, descriptor);
        if (visible) {
            if (tryCatchBlock.visibleTypeAnnotations == null) {
                tryCatchBlock.visibleTypeAnnotations = new ArrayList(1);
            }
            tryCatchBlock.visibleTypeAnnotations.add((Object)typeAnnotation);
        } else {
            if (tryCatchBlock.invisibleTypeAnnotations == null) {
                tryCatchBlock.invisibleTypeAnnotations = new ArrayList(1);
            }
            tryCatchBlock.invisibleTypeAnnotations.add((Object)typeAnnotation);
        }
        return typeAnnotation;
    }

    @Override
    public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
        this.localVariables.add((Object)new LocalVariableNode(name, descriptor, signature, this.getLabelNode(start), this.getLabelNode(end), index));
    }

    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String descriptor, boolean visible) {
        LocalVariableAnnotationNode localVariableAnnotation = new LocalVariableAnnotationNode(typeRef, typePath, this.getLabelNodes(start), this.getLabelNodes(end), index, descriptor);
        if (visible) {
            if (this.visibleLocalVariableAnnotations == null) {
                this.visibleLocalVariableAnnotations = new ArrayList(1);
            }
            this.visibleLocalVariableAnnotations.add((Object)localVariableAnnotation);
        } else {
            if (this.invisibleLocalVariableAnnotations == null) {
                this.invisibleLocalVariableAnnotations = new ArrayList(1);
            }
            this.invisibleLocalVariableAnnotations.add((Object)localVariableAnnotation);
        }
        return localVariableAnnotation;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        this.instructions.add(new LineNumberNode(line, this.getLabelNode(start)));
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
    }

    @Override
    public void visitEnd() {
    }

    protected LabelNode getLabelNode(Label label) {
        if (!(label.info instanceof LabelNode)) {
            label.info = new LabelNode();
        }
        return (LabelNode)label.info;
    }

    private LabelNode[] getLabelNodes(Label[] labels) {
        LabelNode[] labelNodes = new LabelNode[labels.length];
        int n = labels.length;
        for (int i = 0; i < n; ++i) {
            labelNodes[i] = this.getLabelNode(labels[i]);
        }
        return labelNodes;
    }

    private Object[] getLabelNodes(Object[] objects) {
        Object[] labelNodes = new Object[objects.length];
        for (Object o : objects) {
            if (o instanceof Label) {
                o = this.getLabelNode((Label)o);
            }
            labelNodes[i] = o;
        }
        return labelNodes;
    }

    public void check(int api) {
        int i;
        AbstractInsnNode insn;
        if (api == 262144) {
            if (this.parameters != null && !this.parameters.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.tryCatchBlocks != null) {
                for (i = this.tryCatchBlocks.size() - 1; i >= 0; --i) {
                    TryCatchBlockNode tryCatchBlock = (TryCatchBlockNode)this.tryCatchBlocks.get(i);
                    if (tryCatchBlock.visibleTypeAnnotations != null && !tryCatchBlock.visibleTypeAnnotations.isEmpty()) {
                        throw new UnsupportedClassVersionException();
                    }
                    if (tryCatchBlock.invisibleTypeAnnotations == null || tryCatchBlock.invisibleTypeAnnotations.isEmpty()) continue;
                    throw new UnsupportedClassVersionException();
                }
            }
            for (i = this.instructions.size() - 1; i >= 0; --i) {
                boolean isInterface;
                Object value;
                insn = this.instructions.get(i);
                if (insn.visibleTypeAnnotations != null && !insn.visibleTypeAnnotations.isEmpty()) {
                    throw new UnsupportedClassVersionException();
                }
                if (insn.invisibleTypeAnnotations != null && !insn.invisibleTypeAnnotations.isEmpty()) {
                    throw new UnsupportedClassVersionException();
                }
                if (!(insn instanceof MethodInsnNode ? (isInterface = ((MethodInsnNode)insn).itf) != (insn.opcode == 185) : insn instanceof LdcInsnNode && ((value = ((LdcInsnNode)insn).cst) instanceof Handle || value instanceof Type && ((Type)value).getSort() == 11))) continue;
                throw new UnsupportedClassVersionException();
            }
            if (this.visibleLocalVariableAnnotations != null && !this.visibleLocalVariableAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.invisibleLocalVariableAnnotations != null && !this.invisibleLocalVariableAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
        }
        if (api != 458752) {
            for (i = this.instructions.size() - 1; i >= 0; --i) {
                Object value;
                insn = this.instructions.get(i);
                if (!(insn instanceof LdcInsnNode) || !((value = ((LdcInsnNode)insn).cst) instanceof ConstantDynamic)) continue;
                throw new UnsupportedClassVersionException();
            }
        }
    }

    public void accept(ClassVisitor classVisitor) {
        Object[] exceptionsArray = new String[this.exceptions.size()];
        this.exceptions.toArray(exceptionsArray);
        MethodVisitor methodVisitor = classVisitor.visitMethod(this.access, this.name, this.desc, this.signature, (String[])exceptionsArray);
        if (methodVisitor != null) {
            this.accept(methodVisitor);
        }
    }

    public void accept(MethodVisitor methodVisitor) {
        TypeAnnotationNode typeAnnotation;
        int i;
        int m;
        AnnotationNode annotation;
        List<AnnotationNode> parameterAnnotations;
        int n;
        int j;
        AnnotationNode annotation2;
        if (this.parameters != null) {
            n = this.parameters.size();
            for (i = 0; i < n; ++i) {
                ((ParameterNode)this.parameters.get(i)).accept(methodVisitor);
            }
        }
        if (this.annotationDefault != null) {
            AnnotationVisitor annotationVisitor = methodVisitor.visitAnnotationDefault();
            AnnotationNode.accept(annotationVisitor, null, this.annotationDefault);
            if (annotationVisitor != null) {
                annotationVisitor.visitEnd();
            }
        }
        if (this.visibleAnnotations != null) {
            n = this.visibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation2 = (AnnotationNode)this.visibleAnnotations.get(i);
                annotation2.accept(methodVisitor.visitAnnotation(annotation2.desc, true));
            }
        }
        if (this.invisibleAnnotations != null) {
            n = this.invisibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation2 = (AnnotationNode)this.invisibleAnnotations.get(i);
                annotation2.accept(methodVisitor.visitAnnotation(annotation2.desc, false));
            }
        }
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(methodVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
        if (this.visibleAnnotableParameterCount > 0) {
            methodVisitor.visitAnnotableParameterCount(this.visibleAnnotableParameterCount, true);
        }
        if (this.visibleParameterAnnotations != null) {
            n = this.visibleParameterAnnotations.length;
            for (i = 0; i < n; ++i) {
                parameterAnnotations = this.visibleParameterAnnotations[i];
                if (parameterAnnotations == null) continue;
                m = parameterAnnotations.size();
                for (j = 0; j < m; ++j) {
                    annotation = (AnnotationNode)parameterAnnotations.get(j);
                    annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, true));
                }
            }
        }
        if (this.invisibleAnnotableParameterCount > 0) {
            methodVisitor.visitAnnotableParameterCount(this.invisibleAnnotableParameterCount, false);
        }
        if (this.invisibleParameterAnnotations != null) {
            n = this.invisibleParameterAnnotations.length;
            for (i = 0; i < n; ++i) {
                parameterAnnotations = this.invisibleParameterAnnotations[i];
                if (parameterAnnotations == null) continue;
                m = parameterAnnotations.size();
                for (j = 0; j < m; ++j) {
                    annotation = (AnnotationNode)parameterAnnotations.get(j);
                    annotation.accept(methodVisitor.visitParameterAnnotation(i, annotation.desc, false));
                }
            }
        }
        if (this.visited) {
            this.instructions.resetLabels();
        }
        if (this.attrs != null) {
            n = this.attrs.size();
            for (i = 0; i < n; ++i) {
                methodVisitor.visitAttribute((Attribute)this.attrs.get(i));
            }
        }
        if (this.instructions.size() > 0) {
            methodVisitor.visitCode();
            if (this.tryCatchBlocks != null) {
                n = this.tryCatchBlocks.size();
                for (i = 0; i < n; ++i) {
                    ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).updateIndex(i);
                    ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).accept(methodVisitor);
                }
            }
            this.instructions.accept(methodVisitor);
            if (this.localVariables != null) {
                n = this.localVariables.size();
                for (i = 0; i < n; ++i) {
                    ((LocalVariableNode)this.localVariables.get(i)).accept(methodVisitor);
                }
            }
            if (this.visibleLocalVariableAnnotations != null) {
                n = this.visibleLocalVariableAnnotations.size();
                for (i = 0; i < n; ++i) {
                    ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(i)).accept(methodVisitor, true);
                }
            }
            if (this.invisibleLocalVariableAnnotations != null) {
                n = this.invisibleLocalVariableAnnotations.size();
                for (i = 0; i < n; ++i) {
                    ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(i)).accept(methodVisitor, false);
                }
            }
            methodVisitor.visitMaxs(this.maxStack, this.maxLocals);
            this.visited = true;
        }
        methodVisitor.visitEnd();
    }

}

