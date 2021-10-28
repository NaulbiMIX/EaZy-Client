/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.UnsupportedOperationException
 *  java.util.ArrayDeque
 *  java.util.Deque
 *  java.util.Iterator
 *  java.util.List
 */
package net.optifine.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IteratorCache {
    private static Deque<IteratorReusable<Object>> dequeIterators = new ArrayDeque();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Iterator<Object> getReadOnly(List list) {
        Deque<IteratorReusable<Object>> deque = dequeIterators;
        synchronized (deque) {
            IteratorReusable iteratorreusable = (IteratorReusable)dequeIterators.pollFirst();
            if (iteratorreusable == null) {
                iteratorreusable = new IteratorReadOnly();
            }
            iteratorreusable.setList(list);
            return iteratorreusable;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void finished(IteratorReusable<Object> iterator) {
        Deque<IteratorReusable<Object>> deque = dequeIterators;
        synchronized (deque) {
            if (dequeIterators.size() <= 1000) {
                iterator.setList(null);
                dequeIterators.addLast(iterator);
            }
        }
    }

    static {
        for (int i = 0; i < 1000; ++i) {
            IteratorReadOnly iteratorcache$iteratorreadonly = new IteratorReadOnly();
            dequeIterators.add((Object)iteratorcache$iteratorreadonly);
        }
    }

    public static interface IteratorReusable<E>
    extends Iterator<E> {
        public void setList(List<E> var1);
    }

    public static class IteratorReadOnly
    implements IteratorReusable<Object> {
        private List<Object> list;
        private int index;
        private boolean hasNext;

        @Override
        public void setList(List<Object> list) {
            if (this.hasNext) {
                throw new RuntimeException("Iterator still used, oldList: " + this.list + ", newList: " + list);
            }
            this.list = list;
            this.index = 0;
            this.hasNext = list != null && this.index < list.size();
        }

        public Object next() {
            if (!this.hasNext) {
                return null;
            }
            Object object = this.list.get(this.index);
            ++this.index;
            this.hasNext = this.index < this.list.size();
            return object;
        }

        public boolean hasNext() {
            if (!this.hasNext) {
                IteratorCache.finished(this);
                return false;
            }
            return this.hasNext;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}

