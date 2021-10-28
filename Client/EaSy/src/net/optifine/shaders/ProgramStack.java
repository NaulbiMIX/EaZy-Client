/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayDeque
 *  java.util.Deque
 */
package net.optifine.shaders;

import java.util.ArrayDeque;
import java.util.Deque;
import net.optifine.shaders.Program;
import net.optifine.shaders.Shaders;

public class ProgramStack {
    private Deque<Program> stack = new ArrayDeque();

    public void push(Program p) {
        this.stack.addLast((Object)p);
    }

    public Program pop() {
        if (this.stack.isEmpty()) {
            return Shaders.ProgramNone;
        }
        Program program = (Program)this.stack.pollLast();
        return program;
    }
}

