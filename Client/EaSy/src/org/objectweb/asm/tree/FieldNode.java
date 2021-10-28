/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
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
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.TypeAnnotationNode;
import org.objectweb.asm.tree.UnsupportedClassVersionException;

public class FieldNode
extends FieldVisitor {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public Object value;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;

    public FieldNode(int access, String name, String descriptor, String signature, Object value) {
        this(458752, access, name, descriptor, signature, value);
        if (this.getClass() != FieldNode.class) {
            throw new IllegalStateException();
        }
    }

    public FieldNode(int api, int access, String name, String descriptor, String signature, Object value) {
        super(api);
        this.access = access;
        this.name = name;
        this.desc = descriptor;
        this.signature = signature;
        this.value = value;
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
    public void visitAttribute(Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add((Object)attribute);
    }

    @Override
    public void visitEnd() {
    }

    public void check(int api) {
        if (api == 262144) {
            if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
        }
    }

    public void accept(ClassVisitor classVisitor) {
        TypeAnnotationNode typeAnnotation;
        int n;
        AnnotationNode annotation;
        int i;
        FieldVisitor fieldVisitor = classVisitor.visitField(this.access, this.name, this.desc, this.signature, this.value);
        if (fieldVisitor == null) {
            return;
        }
        if (this.visibleAnnotations != null) {
            n = this.visibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = (AnnotationNode)this.visibleAnnotations.get(i);
                annotation.accept(fieldVisitor.visitAnnotation(annotation.desc, true));
            }
        }
        if (this.invisibleAnnotations != null) {
            n = this.invisibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = (AnnotationNode)this.invisibleAnnotations.get(i);
                annotation.accept(fieldVisitor.visitAnnotation(annotation.desc, false));
            }
        }
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(fieldVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(fieldVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
        if (this.attrs != null) {
            n = this.attrs.size();
            for (i = 0; i < n; ++i) {
                fieldVisitor.visitAttribute((Attribute)this.attrs.get(i));
            }
        }
        fieldVisitor.visitEnd();
    }
}

