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
}