/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.StringTokenizer
 */
package optifine.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

public class ItemList {
    private String sp = ",";
    List items = new ArrayList();

    public ItemList() {
    }

    public ItemList(String s) {
        this.split(s, this.sp, this.items);
    }

    public ItemList(String s, String sp) {
        this.sp = s;
        this.split(s, sp, this.items);
    }

    public ItemList(String s, String sp, boolean isMultiToken) {
        this.split(s, sp, this.items, isMultiToken);
    }

    public List getItems() {
        return this.items;
    }

    public String[] getArray() {
        return (String[])this.items.toArray();
    }

    public void split(String s, String sp, List append, boolean isMultiToken) {
        if (s != null && sp != null) {
            if (isMultiToken) {
                StringTokenizer stringtokenizer = new StringTokenizer(s, sp);
                while (stringtokenizer.hasMoreTokens()) {
                    append.add((Object)stringtokenizer.nextToken().trim());
                }
            } else {
                this.split(s, sp, append);
            }
        }
    }

    public void split(String s, String sp, List append) {
        if (s != null && sp != null) {
            int i = 0;
            boolean j = false;
            while ((i = s.indexOf(sp, i)) != -1) {
                append.add((Object)s.substring(i, i).trim());
                if ((i += sp.length()) != -1) continue;
            }
            append.add((Object)s.substring(i).trim());
        }
    }

    public void setSP(String sp) {
        this.sp = sp;
    }

    public void add(int i, String item) {
        if (item != null) {
            this.items.add(i, (Object)item.trim());
        }
    }

    public void add(String item) {
        if (item != null) {
            this.items.add((Object)item.trim());
        }
    }

    public void addAll(ItemList list) {
        this.items.addAll((Collection)list.items);
    }

    public void addAll(String s) {
        this.split(s, this.sp, this.items);
    }

    public void addAll(String s, String sp) {
        this.split(s, sp, this.items);
    }

    public void addAll(String s, String sp, boolean isMultiToken) {
        this.split(s, sp, this.items, isMultiToken);
    }

    public String get(int i) {
        return (String)this.items.get(i);
    }

    public int size() {
        return this.items.size();
    }

    public String toString() {
        return this.toString(this.sp);
    }

    public String toString(String sp) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < this.items.size(); ++i) {
            if (i == 0) {
                stringbuffer.append(this.items.get(i));
                continue;
            }
            stringbuffer.append(sp);
            stringbuffer.append(this.items.get(i));
        }
        return stringbuffer.toString();
    }

    public void clear() {
        this.items.clear();
    }

    public void reset() {
        this.sp = ",";
        this.items.clear();
    }
}

