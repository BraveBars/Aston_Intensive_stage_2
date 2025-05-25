package com.homework.task_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyHashMapTest {

    private MyHashMap<String, Integer> map;


    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void testPutAndGet(){
        assertNull(map.put("key", 1));
        assertEquals(1, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void testPutAndGetNull(){
        map.put(null, 1);
        map.put("key", null);
        assertNull(map.get("key"));
        assertEquals(1, map.get(null));
    }

    @Test
    void testPutOverwriteValue(){
        map.put("key", 1);
        Integer oldValue = map.put("key", 2);
        assertEquals(1, oldValue);
        assertEquals(2, map.get("key"));
        assertEquals(1,map.size());
    }

    @Test
    void testRemove(){
        assertNull(map.remove("NotExists"));
        map.put("key", 5);
        assertEquals(5, map.remove("key"));
        assertNull(map.get("key"));
    }

    @Test
    void testSize(){
        assertEquals(0, map.size());
        map.put("key", 1);
        map.put("key2", 2);
        map.put(null, 3);

        assertEquals(3, map.size());
        map.remove("key2");
        assertEquals(2, map.size());
        map.remove(null);
        assertEquals(1, map.size());
    }

    @Test
    void testResize(){
        MyHashMap<Integer, String> map = new MyHashMap<>(2, 0.75f);

        for (int i = 0; i < 130; i++){
            map.put(i, "value" + i);
            assertEquals(i + 1, map.size());
            assertEquals("value" + i, map.get(i));
        }
    }

    @Test
    void testTableSizeFor() {
        int expectedSize = 1;

        for (int i = 0; i < 130; i++){
            assertEquals(expectedSize, MyHashMap.tableSizeFor(i));

            if(i == expectedSize){
                expectedSize *= 2;
            }
        }

        assertEquals(1 << 30, MyHashMap.tableSizeFor((1 << 30) + 100));
    }

    @Test
    void testToString(){
        map.put("a", 1);
        map.put("b", 2);
        String str = map.toString();

        assertTrue(str.startsWith("{") && str.endsWith("}"));
        assertTrue(str.contains("a = 1") && str.contains("b = 2"));
    }

    @Test
    void testConstructorExceptions(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new MyHashMap<>(-1);
        });
        assertTrue(e.getMessage().contains("Illegal initial capacity: -1"));

        Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
            new MyHashMap<>(2, -0.5f);
        });
        assertTrue(e2.getMessage().contains("Illegal load factor: -0.5"));

        Exception e3 = assertThrows(IllegalArgumentException.class, () -> {
            new MyHashMap<>(2, Float.NaN);
        });
        assertTrue(e3.getMessage().contains("Illegal load factor: NaN"));
    }
}