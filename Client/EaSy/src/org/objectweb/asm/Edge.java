/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.objectweb.asm;

import org.objectweb.asm.Label;

final class Edge {
    static final int JUMP = 0;
    static final int EXCEPTION = Integer.MAX_VALUE;
    final int info;
    final Label successor;
    Edge nextEdge;

    Edge(int info, Label successor, Edge nextEdge) {
        this.info = info;
        this.successor = successor;
        this.nextEdge = nextEdge;
    }
}

