/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.List
 *  java.util.Map
 */
package optifine.json;

import java.util.List;
import java.util.Map;

public interface ContainerFactory {
    public Map createObjectContainer();

    public List creatArrayContainer();
}

