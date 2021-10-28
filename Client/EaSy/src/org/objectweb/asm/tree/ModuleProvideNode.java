/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package org.objectweb.asm.tree;

import java.util.List;
import org.objectweb.asm.ModuleVisitor;

public class ModuleProvideNode {
    public String service;
    public List<String> providers;

    public ModuleProvideNode(String service, List<String> providers) {
        this.service = service;
        this.providers = providers;
    }

    public void accept(ModuleVisitor moduleVisitor) {
        moduleVisitor.visitProvide(this.service, (String[])this.providers.toArray((Object[])new String[0]));
    }
}

