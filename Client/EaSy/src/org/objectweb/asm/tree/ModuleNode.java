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
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.tree.ModuleExportNode;
import org.objectweb.asm.tree.ModuleOpenNode;
import org.objectweb.asm.tree.ModuleProvideNode;
import org.objectweb.asm.tree.ModuleRequireNode;
import org.objectweb.asm.tree.Util;

public class ModuleNode
extends ModuleVisitor {
    public String name;
    public int access;
    public String version;
    public String mainClass;
    public List<String> packages;
    public List<ModuleRequireNode> requires;
    public List<ModuleExportNode> exports;
    public List<ModuleOpenNode> opens;
    public List<String> uses;
    public List<ModuleProvideNode> provides;

    public ModuleNode(String name, int access, String version) {
        super(458752);
        if (this.getClass() != ModuleNode.class) {
            throw new IllegalStateException();
        }
        this.name = name;
        this.access = access;
        this.version = version;
    }

    public ModuleNode(int api, String name, int access, String version, List<ModuleRequireNode> requires, List<ModuleExportNode> exports, List<ModuleOpenNode> opens, List<String> uses, List<ModuleProvideNode> provides) {
        super(api);
        this.name = name;
        this.access = access;
        this.version = version;
        this.requires = requires;
        this.exports = exports;
        this.opens = opens;
        this.uses = uses;
        this.provides = provides;
    }

    @Override
    public void visitMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public void visitPackage(String packaze) {
        if (this.packages == null) {
            this.packages = new ArrayList(5);
        }
        this.packages.add((Object)packaze);
    }

    @Override
    public void visitRequire(String module, int access, String version) {
        if (this.requires == null) {
            this.requires = new ArrayList(5);
        }
        this.requires.add((Object)new ModuleRequireNode(module, access, version));
    }

    @Override
    public /* varargs */ void visitExport(String packaze, int access, String ... modules) {
        if (this.exports == null) {
            this.exports = new ArrayList(5);
        }
        this.exports.add((Object)new ModuleExportNode(packaze, access, Util.asArrayList(modules)));
    }

    @Override
    public /* varargs */ void visitOpen(String packaze, int access, String ... modules) {
        if (this.opens == null) {
            this.opens = new ArrayList(5);
        }
        this.opens.add((Object)new ModuleOpenNode(packaze, access, Util.asArrayList(modules)));
    }

    @Override
    public void visitUse(String service) {
        if (this.uses == null) {
            this.uses = new ArrayList(5);
        }
        this.uses.add((Object)service);
    }

    @Override
    public /* varargs */ void visitProvide(String service, String ... providers) {
        if (this.provides == null) {
            this.provides = new ArrayList(5);
        }
        this.provides.add((Object)new ModuleProvideNode(service, Util.asArrayList(providers)));
    }

    @Override
    public void visitEnd() {
    }

    public void accept(ClassVisitor classVisitor) {
        int n;
        int i;
        ModuleVisitor moduleVisitor = classVisitor.visitModule(this.name, this.access, this.version);
        if (moduleVisitor == null) {
            return;
        }
        if (this.mainClass != null) {
            moduleVisitor.visitMainClass(this.mainClass);
        }
        if (this.packages != null) {
            n = this.packages.size();
            for (i = 0; i < n; ++i) {
                moduleVisitor.visitPackage((String)this.packages.get(i));
            }
        }
        if (this.requires != null) {
            n = this.requires.size();
            for (i = 0; i < n; ++i) {
                ((ModuleRequireNode)this.requires.get(i)).accept(moduleVisitor);
            }
        }
        if (this.exports != null) {
            n = this.exports.size();
            for (i = 0; i < n; ++i) {
                ((ModuleExportNode)this.exports.get(i)).accept(moduleVisitor);
            }
        }
        if (this.opens != null) {
            n = this.opens.size();
            for (i = 0; i < n; ++i) {
                ((ModuleOpenNode)this.opens.get(i)).accept(moduleVisitor);
            }
        }
        if (this.uses != null) {
            n = this.uses.size();
            for (i = 0; i < n; ++i) {
                moduleVisitor.visitUse((String)this.uses.get(i));
            }
        }
        if (this.provides != null) {
            n = this.provides.size();
            for (i = 0; i < n; ++i) {
                ((ModuleProvideNode)this.provides.get(i)).accept(moduleVisitor);
            }
        }
    }
}

