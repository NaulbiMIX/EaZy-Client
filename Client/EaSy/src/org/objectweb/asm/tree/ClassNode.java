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
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ModuleNode;
import org.objectweb.asm.tree.TypeAnnotationNode;
import org.objectweb.asm.tree.UnsupportedClassVersionException;
import org.objectweb.asm.tree.Util;

public class ClassNode
extends ClassVisitor {
    public int version;
    public int access;
    public String name;
    public String signature;
    public String superName;
    public List<String> interfaces = new ArrayList();
    public String sourceFile;
    public String sourceDebug;
    public ModuleNode module;
    public String outerClass;
    public String outerMethod;
    public String outerMethodDesc;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public List<InnerClassNode> innerClasses = new ArrayList();
    public String nestHostClass;
    public List<String> nestMembers;
    public List<FieldNode> fields = new ArrayList();
    public List<MethodNode> methods = new ArrayList();

    public ClassNode() {
        this(458752);
        if (this.getClass() != ClassNode.class) {
            throw new IllegalStateException();
        }
    }

    public ClassNode(int api) {
        super(api);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = Util.asArrayList(interfaces);
    }

    @Override
    public void visitSource(String file, String debug) {
        this.sourceFile = file;
        this.sourceDebug = debug;
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        this.module = new ModuleNode(name, access, version);
        return this.module;
    }

    @Override
    public void visitNestHost(String nestHost) {
        this.nestHostClass = nestHost;
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        this.outerClass = owner;
        this.outerMethod = name;
        this.outerMethodDesc = descriptor;
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
    public void visitNestMember(String nestMember) {
        if (this.nestMembers == null) {
            this.nestMembers = new ArrayList();
        }
        this.nestMembers.add((Object)nestMember);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        InnerClassNode innerClass = new InnerClassNode(name, outerName, innerName, access);
        this.innerClasses.add((Object)innerClass);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldNode field = new FieldNode(access, name, descriptor, signature, value);
        this.fields.add((Object)field);
        return field;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodNode method = new MethodNode(access, name, descriptor, signature, exceptions);
        this.methods.add((Object)method);
        return method;
    }

    @Override
    public void visitEnd() {
    }

    public void check(int api) {
        int i;
        if (api < 458752 && (this.nestHostClass != null || this.nestMembers != null)) {
            throw new UnsupportedClassVersionException();
        }
        if (api < 393216 && this.module != null) {
            throw new UnsupportedClassVersionException();
        }
        if (api < 327680) {
            if (this.visibleTypeAnnotations != null && !this.visibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
            if (this.invisibleTypeAnnotations != null && !this.invisibleTypeAnnotations.isEmpty()) {
                throw new UnsupportedClassVersionException();
            }
        }
        if (this.visibleAnnotations != null) {
            for (i = this.visibleAnnotations.size() - 1; i >= 0; --i) {
                ((AnnotationNode)this.visibleAnnotations.get(i)).check(api);
            }
        }
        if (this.invisibleAnnotations != null) {
            for (i = this.invisibleAnnotations.size() - 1; i >= 0; --i) {
                ((AnnotationNode)this.invisibleAnnotations.get(i)).check(api);
            }
        }
        if (this.visibleTypeAnnotations != null) {
            for (i = this.visibleTypeAnnotations.size() - 1; i >= 0; --i) {
                ((TypeAnnotationNode)this.visibleTypeAnnotations.get(i)).check(api);
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            for (i = this.invisibleTypeAnnotations.size() - 1; i >= 0; --i) {
                ((TypeAnnotationNode)this.invisibleTypeAnnotations.get(i)).check(api);
            }
        }
        for (i = this.fields.size() - 1; i >= 0; --i) {
            ((FieldNode)this.fields.get(i)).check(api);
        }
        for (i = this.methods.size() - 1; i >= 0; --i) {
            ((MethodNode)this.methods.get(i)).check(api);
        }
    }

    public void accept(ClassVisitor classVisitor) {
        TypeAnnotationNode typeAnnotation;
        int n;
        AnnotationNode annotation;
        int i;
        Object[] interfacesArray = new String[this.interfaces.size()];
        this.interfaces.toArray(interfacesArray);
        classVisitor.visit(this.version, this.access, this.name, this.signature, this.superName, (String[])interfacesArray);
        if (this.sourceFile != null || this.sourceDebug != null) {
            classVisitor.visitSource(this.sourceFile, this.sourceDebug);
        }
        if (this.module != null) {
            this.module.accept(classVisitor);
        }
        if (this.nestHostClass != null) {
            classVisitor.visitNestHost(this.nestHostClass);
        }
        if (this.outerClass != null) {
            classVisitor.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
        }
        if (this.visibleAnnotations != null) {
            n = this.visibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = (AnnotationNode)this.visibleAnnotations.get(i);
                annotation.accept(classVisitor.visitAnnotation(annotation.desc, true));
            }
        }
        if (this.invisibleAnnotations != null) {
            n = this.invisibleAnnotations.size();
            for (i = 0; i < n; ++i) {
                annotation = (AnnotationNode)this.invisibleAnnotations.get(i);
                annotation.accept(classVisitor.visitAnnotation(annotation.desc, false));
            }
        }
        if (this.visibleTypeAnnotations != null) {
            n = this.visibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.visibleTypeAnnotations.get(i);
                typeAnnotation.accept(classVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, true));
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            n = this.invisibleTypeAnnotations.size();
            for (i = 0; i < n; ++i) {
                typeAnnotation = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(i);
                typeAnnotation.accept(classVisitor.visitTypeAnnotation(typeAnnotation.typeRef, typeAnnotation.typePath, typeAnnotation.desc, false));
            }
        }
        if (this.attrs != null) {
            n = this.attrs.size();
            for (i = 0; i < n; ++i) {
                classVisitor.visitAttribute((Attribute)this.attrs.get(i));
            }
        }
        if (this.nestMembers != null) {
            n = this.nestMembers.size();
            for (i = 0; i < n; ++i) {
                classVisitor.visitNestMember((String)this.nestMembers.get(i));
            }
        }
        n = this.innerClasses.size();
        for (i = 0; i < n; ++i) {
            ((InnerClassNode)this.innerClasses.get(i)).accept(classVisitor);
        }
        n = this.fields.size();
        for (i = 0; i < n; ++i) {
            ((FieldNode)this.fields.get(i)).accept(classVisitor);
        }
        n = this.methods.size();
        for (i = 0; i < n; ++i) {
            ((MethodNode)this.methods.get(i)).accept(classVisitor);
        }
        classVisitor.visitEnd();
    }
}

