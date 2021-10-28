/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package net.optifine.render;

import net.optifine.util.LinkedList;

public class VboRange {
    private int position = -1;
    private int size = 0;
    private LinkedList.Node<VboRange> node = new LinkedList.Node<VboRange>(this);

    public int getPosition() {
        return this.position;
    }

    public int getSize() {
        return this.size;
    }

    public int getPositionNext() {
        return this.position + this.size;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedList.Node<VboRange> getNode() {
        return this.node;
    }

    public VboRange getPrev() {
        LinkedList.Node<VboRange> node = this.node.getPrev();
        return node == null ? null : node.getItem();
    }

    public VboRange getNext() {
        LinkedList.Node<VboRange> node = this.node.getNext();
        return node == null ? null : node.getItem();
    }

    public String toString() {
        return "" + this.position + "/" + this.size + "/" + (this.position + this.size);
    }
}

