/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 */
package optifine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodNode;

public class AccessFixer {
    public static void fixMemberAccess(ClassNode classOld, ClassNode classNew) {
        List<FieldNode> list = classOld.fields;
        List<FieldNode> list1 = classNew.fields;
        Map<String, FieldNode> map = AccessFixer.getMapFields(list);
        for (FieldNode fieldnode : list1) {
            String s = fieldnode.name;
            FieldNode fieldnode1 = (FieldNode)map.get((Object)s);
            if (fieldnode1 == null || fieldnode.access == fieldnode1.access) continue;
            fieldnode.access = AccessFixer.combineAccess(fieldnode.access, fieldnode1.access);
        }
        List<MethodNode> list2 = classOld.methods;
        List<MethodNode> list3 = classNew.methods;
        Map<String, MethodNode> map1 = AccessFixer.getMapMethods(list2);
        for (MethodNode methodnode : list3) {
            String s1 = methodnode.name + methodnode.desc;
            Object methodnode1 = (MethodNode)map1.get((Object)s1);
            if (methodnode1 == null || methodnode.access == methodnode1.access) continue;
            methodnode.access = AccessFixer.combineAccess(methodnode.access, methodnode1.access);
        }
        List<InnerClassNode> list4 = classOld.innerClasses;
        List<InnerClassNode> list5 = classNew.innerClasses;
        Map<String, InnerClassNode> map2 = AccessFixer.getMapInnerClasses(list4);
        for (InnerClassNode innerclassnode : list5) {
            int i;
            String s2 = innerclassnode.name;
            InnerClassNode innerclassnode1 = (InnerClassNode)map2.get((Object)s2);
            if (innerclassnode1 == null || innerclassnode.access == innerclassnode1.access) continue;
            innerclassnode.access = i = AccessFixer.combineAccess(innerclassnode.access, innerclassnode1.access);
        }
        if (classNew.access != classOld.access) {
            int j;
            classNew.access = j = AccessFixer.combineAccess(classNew.access, classOld.access);
        }
    }

    private static int combineAccess(int access, int access2) {
        if (access == access2) {
            return access;
        }
        int i = 7;
        int j = access & ~i;
        if (!AccessFixer.isSet(access, 16) || !AccessFixer.isSet(access2, 16)) {
            j &= -17;
        }
        if (!AccessFixer.isSet(access, 1) && !AccessFixer.isSet(access2, 1)) {
            if (!AccessFixer.isSet(access, 4) && !AccessFixer.isSet(access2, 4)) {
                return !AccessFixer.isSet(access, 2) && !AccessFixer.isSet(access2, 2) ? j : j | 2;
            }
            return j | 4;
        }
        return j | 1;
    }

    private static boolean isSet(int access, int flag) {
        return (access & flag) != 0;
    }

    public static Map<String, FieldNode> getMapFields(List<FieldNode> fields) {
        LinkedHashMap map = new LinkedHashMap();
        for (FieldNode fieldnode : fields) {
            String s = fieldnode.name;
            map.put((Object)s, (Object)fieldnode);
        }
        return map;
    }

    public static Map<String, MethodNode> getMapMethods(List<MethodNode> methods) {
        LinkedHashMap map = new LinkedHashMap();
        for (MethodNode methodnode : methods) {
            String s = methodnode.name + methodnode.desc;
            map.put((Object)s, (Object)methodnode);
        }
        return map;
    }

    public static Map<String, InnerClassNode> getMapInnerClasses(List<InnerClassNode> innerClasses) {
        LinkedHashMap map = new LinkedHashMap();
        for (InnerClassNode innerclassnode : innerClasses) {
            String s = innerclassnode.name;
            map.put((Object)s, (Object)innerclassnode);
        }
        return map;
    }
}

