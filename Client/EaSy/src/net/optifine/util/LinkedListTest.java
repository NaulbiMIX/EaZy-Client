/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Random
 */
package net.optifine.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;
import net.optifine.util.LinkedList;

public class LinkedListTest {
    public static void main(String[] args) throws Exception {
        LinkedList linkedlist = new LinkedList();
        ArrayList list = new ArrayList();
        ArrayList list1 = new ArrayList();
        Random random = new Random();
        int i = 100;
        for (int j = 0; j < i; ++j) {
            VboRange vborange = new VboRange();
            vborange.setPosition(j);
            list.add((Object)vborange);
        }
        for (int k = 0; k < 100000; ++k) {
            LinkedListTest.checkLists((List<VboRange>)list, (List<VboRange>)list1, i);
            LinkedListTest.checkLinkedList(linkedlist, list1.size());
            if (k % 5 == 0) {
                LinkedListTest.dbgLinkedList(linkedlist);
            }
            if (random.nextBoolean()) {
                if (list.isEmpty()) continue;
                VboRange vborange3 = (VboRange)list.get(random.nextInt(list.size()));
                LinkedList.Node<VboRange> node2 = vborange3.getNode();
                if (random.nextBoolean()) {
                    linkedlist.addFirst(node2);
                    LinkedListTest.dbg("Add first: " + vborange3.getPosition());
                } else if (random.nextBoolean()) {
                    linkedlist.addLast(node2);
                    LinkedListTest.dbg("Add last: " + vborange3.getPosition());
                } else {
                    if (list1.isEmpty()) continue;
                    VboRange vborange1 = (VboRange)list1.get(random.nextInt(list1.size()));
                    LinkedList.Node<VboRange> node1 = vborange1.getNode();
                    linkedlist.addAfter(node1, node2);
                    LinkedListTest.dbg("Add after: " + vborange1.getPosition() + ", " + vborange3.getPosition());
                }
                list.remove((Object)vborange3);
                list1.add((Object)vborange3);
                continue;
            }
            if (list1.isEmpty()) continue;
            VboRange vborange2 = (VboRange)list1.get(random.nextInt(list1.size()));
            LinkedList.Node<VboRange> node = vborange2.getNode();
            linkedlist.remove(node);
            LinkedListTest.dbg("Remove: " + vborange2.getPosition());
            list1.remove((Object)vborange2);
            list.add((Object)vborange2);
        }
    }

    private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
        StringBuffer stringbuffer = new StringBuffer();
        for (Object x : (List)linkedList) {
            LinkedList.Node node = (LinkedList.Node)x;
            VboRange vborange = (VboRange)node.getItem();
            if (stringbuffer.length() > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(vborange.getPosition());
        }
        LinkedListTest.dbg("List: " + (Object)stringbuffer);
    }

    private static void checkLinkedList(LinkedList<VboRange> linkedList, int used) {
        if (linkedList.getSize() != used) {
            throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used);
        }
        int i = 0;
        for (LinkedList.Node<VboRange> node = linkedList.getFirst(); node != null; node = node.getNext()) {
            ++i;
        }
        if (linkedList.getSize() != i) {
            throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + i);
        }
        int j = 0;
        for (LinkedList.Node<VboRange> node1 = linkedList.getLast(); node1 != null; node1 = node1.getPrev()) {
            ++j;
        }
        if (linkedList.getSize() != j) {
            throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + j);
        }
    }

    private static void checkLists(List<VboRange> listFree, List<VboRange> listUsed, int count) {
        int i = listFree.size() + listUsed.size();
        if (i != count) {
            throw new RuntimeException("Total size: " + i);
        }
    }

    private static void dbg(String str) {
        System.out.println(str);
    }
}

