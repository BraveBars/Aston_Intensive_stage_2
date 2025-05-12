package com.homework.task_1;

import java.util.Objects;

public class MyHashMap<K, V>{

    static class Node<K, V>{
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

        public final int hashCode(){
            return (Objects.hashCode(key) ^ value.hashCode());
        }

        public final boolean equals(Object o){
            if (o == this){
                return true;
            }

            if (o instanceof Node){
                Node<?, ?> e = (Node<?, ?>) o;
                return Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
            }

            return false;
        }
    }
}


