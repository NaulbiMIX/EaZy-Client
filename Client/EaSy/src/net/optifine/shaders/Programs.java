/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package net.optifine.shaders;

import java.util.ArrayList;
import java.util.List;
import net.optifine.shaders.Program;
import net.optifine.shaders.ProgramStage;

public class Programs {
    private List<Program> programs = new ArrayList();
    private Program programNone = this.make("", ProgramStage.NONE, true);

    public Program make(String name, ProgramStage programStage, Program backupProgram) {
        int i = this.programs.size();
        Program program = new Program(i, name, programStage, backupProgram);
        this.programs.add((Object)program);
        return program;
    }

    private Program make(String name, ProgramStage programStage, boolean ownBackup) {
        int i = this.programs.size();
        Program program = new Program(i, name, programStage, ownBackup);
        this.programs.add((Object)program);
        return program;
    }

    public Program makeGbuffers(String name, Program backupProgram) {
        return this.make(name, ProgramStage.GBUFFERS, backupProgram);
    }

    public Program makeComposite(String name) {
        return this.make(name, ProgramStage.COMPOSITE, this.programNone);
    }

    public Program makeDeferred(String name) {
        return this.make(name, ProgramStage.DEFERRED, this.programNone);
    }

    public Program makeShadow(String name, Program backupProgram) {
        return this.make(name, ProgramStage.SHADOW, backupProgram);
    }

    public Program makeVirtual(String name) {
        return this.make(name, ProgramStage.NONE, true);
    }

    public Program[] makeComposites(String prefix, int count) {
        Program[] aprogram = new Program[count];
        for (int i = 0; i < count; ++i) {
            String s = i == 0 ? prefix : prefix + i;
            aprogram[i] = this.makeComposite(s);
        }
        return aprogram;
    }

    public Program[] makeDeferreds(String prefix, int count) {
        Program[] aprogram = new Program[count];
        for (int i = 0; i < count; ++i) {
            String s = i == 0 ? prefix : prefix + i;
            aprogram[i] = this.makeDeferred(s);
        }
        return aprogram;
    }

    public Program getProgramNone() {
        return this.programNone;
    }

    public int getCount() {
        return this.programs.size();
    }

    public Program getProgram(String name) {
        if (name == null) {
            return null;
        }
        for (int i = 0; i < this.programs.size(); ++i) {
            Program program = (Program)this.programs.get(i);
            String s = program.getName();
            if (!s.equals((Object)name)) continue;
            return program;
        }
        return null;
    }

    public String[] getProgramNames() {
        String[] astring = new String[this.programs.size()];
        for (int i = 0; i < astring.length; ++i) {
            astring[i] = ((Program)this.programs.get(i)).getName();
        }
        return astring;
    }

    public Program[] getPrograms() {
        Program[] aprogram = (Program[])this.programs.toArray((Object[])new Program[this.programs.size()]);
        return aprogram;
    }

    public Program[] getPrograms(Program programFrom, Program programTo) {
        int j;
        int i = programFrom.getIndex();
        if (i > (j = programTo.getIndex())) {
            int k = i;
            i = j;
            j = k;
        }
        Program[] aprogram = new Program[j - i + 1];
        for (int l = 0; l < aprogram.length; ++l) {
            aprogram[l] = (Program)this.programs.get(i + l);
        }
        return aprogram;
    }

    public String toString() {
        return this.programs.toString();
    }
}

