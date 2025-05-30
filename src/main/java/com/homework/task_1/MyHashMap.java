package com.homework.task_1;

import java.util.Objects;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        if (n < 0) {
            return 1;
        } else {
            if (n > MAXIMUM_CAPACITY) {
                return MAXIMUM_CAPACITY;
            } else {
                return n + 1;
            }
        }
    }

    static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public final String toString() {
            return key + " = " + value;
        }

        public final int hashCode() {
            return (Objects.hashCode(key) ^ Objects.hashCode(value));
        }

        public final boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Node) {
                Node<?, ?> e = (Node<?, ?>) o;
                return Objects.equals(key, e.getKey())
                        && Objects.equals(value, e.getValue());
            }
            return false;
        }
    }

    private Node<K, V>[] table;
    private int size;
    private int threshold;
    private final float loadFactor;

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        int actualCapacity = tableSizeFor(initialCapacity);
        this.loadFactor = loadFactor;
        this.threshold = (int) (actualCapacity * loadFactor);
        this.table = (Node<K, V>[]) new Node[actualCapacity];
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    static final int hash(Object key) {
        if (key != null) {
            int h = key.hashCode();
            return h ^ (h >>> 16);
        }
        return 0;
    }

    public int size() {
        return size;
    }

    public V put(K key, V value) {
        int h = hash(key);
        int index = (table.length - 1) & h;

        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == h && Objects.equals(e.key, key)) {
                V oldValue = e.value;
                e.value = value;
                return oldValue;
            }
        }

        Node<K, V> newNode = new Node<>(h, key, value, table[index]);
        table[index] = newNode;
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = table.length;
        int newCapacity = oldCapacity << 1;
        if (oldCapacity >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Node<K, V>[] oldTable = table;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> e : oldTable) {
            while (e != null) {
                Node<K, V> next = e.next;
                int index = (newCapacity - 1) & e.hash;
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    public V get(Object key) {
        int h = hash(key);
        int index = (table.length - 1) & h;
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == h && Objects.equals(e.key, key)) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(Object key) {
        int h = hash(key);
        int index = (table.length - 1) & h;
        Node<K, V> prev = null;
        for (Node<K, V> e = table[index]; e != null; prev = e, e = e.next) {
            if (e.hash == h && Objects.equals(e.key, key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Node<K, V> bucket : table) {
            for (Node<K, V> e = bucket; e != null; e = e.next) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(e.toString());
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }
}


