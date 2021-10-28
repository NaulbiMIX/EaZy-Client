/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Iterator
 */
package net.optifine.util;

import java.util.Iterator;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public void addFirst(Node<T> node) {
        this.checkNoParent(node);
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        } else {
            Node<T> nodeNext = this.first;
            node.setNext(nodeNext);
            node.setPrev(node);
            this.first = node;
        }
        node.setParent(this);
        ++this.size;
    }

    public void addLast(Node<T> node) {
        this.checkNoParent(node);
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        } else {
            Node<T> nodePrev = this.last;
            node.setPrev(nodePrev);
            node.setNext(node);
            this.last = node;
        }
        node.setParent(this);
        ++this.size;
    }

    public void addAfter(Node<T> nodePrev, Node<T> node) {
        if (nodePrev == null) {
            this.addFirst(node);
            return;
        }
        if (nodePrev == this.last) {
            this.addLast(node);
            return;
        }
        this.checkParent(nodePrev);
        this.checkNoParent(node);
        Node<T> nodeNext = nodePrev.getNext();
        nodePrev.setNext(node);
        node.setPrev(nodePrev);
        nodeNext.setPrev(node);
        node.setNext(nodeNext);
        node.setParent(this);
        ++this.size;
    }

    public Node<T> remove(Node<T> node) {
        this.checkParent(node);
        Node<T> prev = node.getPrev();
        Node<T> next = node.getNext();
        if (prev != null) {
            prev.setNext(next);
        } else {
            this.first = next;
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            this.last = prev;
        }
        node.setPrev(null);
        node.setNext(null);
        node.setParent(null);
        --this.size;
        return node;
    }

    public void moveAfter(Node<T> nodePrev, Node<T> node) {
        this.remove(node);
        this.addAfter(nodePrev, node);
    }

    public boolean find(Node<T> nodeFind, Node<T> nodeFrom, Node<T> nodeTo) {
        Node<T> node;
        this.checkParent(nodeFrom);
        if (nodeTo != null) {
            this.checkParent(nodeTo);
        }
        for (node = nodeFrom; node != null && node != nodeTo; node = node.getNext()) {
            if (node != nodeFind) continue;
            return true;
        }
        if (node != nodeTo) {
            throw new IllegalArgumentException("Sublist is not linked, from: " + nodeFrom + ", to: " + nodeTo);
        }
        return false;
    }

    private void checkParent(Node<T> node) {
        if (node.parent != this) {
            throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
        }
    }

    private void checkNoParent(Node<T> node) {
        if (node.parent != null) {
            throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
        }
    }

    public boolean contains(Node<T> node) {
        return node.parent == this;
    }

    public Iterator<Node<T>> iterator() {
        Iterator iterator = new Iterator<Node<T>>(){
            Node<T> node;
            {
                this.node = LinkedList.this.getFirst();
            }

            public boolean hasNext() {
                return this.node != null;
            }

            public Node<T> next() {
                Node<T> node = this.node;
                if (this.node != null) {
                    this.node = this.node.next;
                }
                return node;
            }
        };
        return iterator;
    }

    public Node<T> getFirst() {
        return this.first;
    }

    public Node<T> getLast() {
        return this.last;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size <= 0;
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        return "" + this.size + " [" + stringbuffer.toString() + "]";
    }

    public static class Node<T> {
        private final T item;
        private Node<T> prev;
        private Node<T> next;
        private LinkedList<T> parent;

        public Node(T item) {
            this.item = item;
        }

        public T getItem() {
            return this.item;
        }

        public Node<T> getPrev() {
            return this.prev;
        }

        public Node<T> getNext() {
            return this.next;
        }

        private void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        private void setNext(Node<T> next) {
            this.next = next;
        }

        private void setParent(LinkedList<T> parent) {
            this.parent = parent;
        }

        public String toString() {
            return "" + this.item;
        }
    }

}

