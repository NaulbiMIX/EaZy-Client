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
import org.objectweb.asm.tree.Util;

public class AnnotationNode
extends AnnotationVisitor {
    public String desc;
    public List<Object> values;

    public AnnotationNode(String descriptor) {
        this(458752, descriptor);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int api, String descriptor) {
        super(api);
        this.desc = descriptor;
    }

    AnnotationNode(List<Object> values) {
        super(458752);
        this.values = values;
    }

    @Override
    public void visit(String name, Object value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add((Object)name);
        }
        if (value instanceof byte[]) {
            this.values.add(Util.asArrayList((byte[])value));
        } else if (value instanceof boolean[]) {
            this.values.add(Util.asArrayList((boolean[])value));
        } else if (value instanceof short[]) {
            this.values.add(Util.asArrayList((short[])value));
        } else if (value instanceof char[]) {
            this.values.add(Util.asArrayList((char[])value));
        } else if (value instanceof int[]) {
            this.values.add(Util.asArrayList((int[])value));
        } else if (value instanceof long[]) {
            this.values.add(Util.asArrayList((long[])value));
        } else if (value instanceof float[]) {
            this.values.add(Util.asArrayList((float[])value));
        } else if (value instanceof double[]) {
            this.values.add(Util.asArrayList((double[])value));
        } else {
            this.values.add(value);
        }
    }

    @Override
    public void visitEnum(String name, String descriptor, String value) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add((Object)name);
        }
        this.values.add((Object)new String[]{descriptor, value});
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add((Object)name);
        }
        AnnotationNode annotation = new AnnotationNode(descriptor);
        this.values.add((Object)annotation);
        return annotation;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        if (this.values == null) {
            this.values = new ArrayList(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add((Object)name);
        }
        ArrayList array = new ArrayList();
        this.values.add((Object)array);
        return new AnnotationNode((List<Object>)array);
    }

    @Override
    public void visitEnd() {
    }

    public void check(int api) {
    }

    public void accept(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            if (this.values != null) {
                int n = this.values.size();
                for (int i = 0; i < n; i += 2) {
                    String name = (String)this.values.get(i);
                    Object value = this.values.get(i + 1);
                    AnnotationNode.accept(annotationVisitor, name, value);
                }
            }
            annotationVisitor.visitEnd();
        }
    }

    static void accept(AnnotationVisitor annotationVisitor, String name, Object value) {
        if (annotationVisitor != null) {
            if (value instanceof String[]) {
                String[] typeValue = (String[])value;
                annotationVisitor.visitEnum(name, typeValue[0], typeValue[1]);
            } else if (value instanceof AnnotationNode) {
                AnnotationNode annotationValue = (AnnotationNode)value;
                annotationValue.accept(annotationVisitor.visitAnnotation(name, annotationValue.desc));
            } else if (value instanceof List) {
                AnnotationVisitor arrayAnnotationVisitor = annotationVisitor.visitArray(name);
                if (arrayAnnotationVisitor != null) {
                    List arrayValue = (List)value;
                    int n = arrayValue.size();
                    for (int i = 0; i < n; ++i) {
                        AnnotationNode.accept(arrayAnnotationVisitor, null, arrayValue.get(i));
                    }
                    arrayAnnotationVisitor.visitEnd();
                }
            } else {
                annotationVisitor.visit(name, value);
            }
        }
    }
}

