package com.kevin.datastructures.map;

import com.kevin.datastructures.Asserts;
import com.kevin.datastructures.map.model.Key;

import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
/*
        testHashMapPutGet();
        test2();
        test3(new LinkedHashMap<>());
        test4(new LinkedHashMap<>());
        test5(new LinkedHashMap<>());
*/
        testBloomFilter();
    }

    private static void testBloomFilter(){
        BloomFilter<Integer> bloomFilter = new BloomFilter<>(1_000_0000, 0.01);
        for (int i = 1; i <= 1_000_0000; i++) {
            bloomFilter.put(i);
        }

        int count = 0;
        for (int i = 1_000_0001; i <= 2_000_0000; i++) {
            if (bloomFilter.contains(i)) {//误判率
                count++;
            }
        }
        System.out.println(count);


    }

    private static void testHashMapPutGet() {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("hello", 123);
        hashMap.put("lily", 35);
        hashMap.put("kevin", 783);
        System.out.println(hashMap.get("hello"));
        System.out.println(hashMap.get("lily"));
        System.out.println(hashMap.get("kevin"));
    }


    static void test2() {
        HashMap<Object, Integer> map = new HashMap<>();
        for (int i = 1; i <= 20; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            map.put(new Key(i), i + 5);//替换5,6,7
        }
        Asserts.test(map.size() == 20);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == 10);
        Asserts.test(map.get(new Key(6)) == 11);
        Asserts.test(map.get(new Key(7)) == 12);
        Asserts.test(map.get(new Key(8)) == 8);
//        map.print();
    }

    static void test3(HashMap<Object, Integer> map) {
//        HashMap<Object, Integer> map = new HashMap<>();
        map.put(null, 1); // 1
        map.put(new Object(), 2); // 2
        map.put("jack", 3); // 3
        map.put(10, 4); // 4
        map.put(new Object(), 5); // 5
        map.put("jack", 6);
        map.put(10, 7);
        map.put(null, 8);
        map.put(10, null);
        Asserts.test(map.size() == 5);
        Asserts.test(map.get(null) == 8);
        Asserts.test(map.get("jack") == 6);
        Asserts.test(map.get(10) == null);
        Asserts.test(map.get(new Object()) == null);
        Asserts.test(map.containsKey(10));
        Asserts.test(map.containsKey(null));
        Asserts.test(map.containsValue(null));
        Asserts.test(map.containsValue(1) == false);
    }

    static void test4(HashMap<Object, Integer> map) {
//        HashMap<Object, Integer> map = new HashMap<>();
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jim", 3);
        map.put("jake", 4);
        map.remove("jack");
        map.remove("jim");
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            Asserts.test(map.remove(new Key(i)) == i);
        }
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 19);
        Asserts.test(map.get(new Key(1)) == 6);
        Asserts.test(map.get(new Key(2)) == 7);
        Asserts.test(map.get(new Key(3)) == 8);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == null);
        Asserts.test(map.get(new Key(6)) == null);
        Asserts.test(map.get(new Key(7)) == null);
        Asserts.test(map.get(new Key(8)) == 8);
        map.traversal(new Map.Visitor<Object, Integer>() {
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    private static void test5(HashMap<Object, Integer> map) {
//        java.util.HashMap<Object, Integer> map = new java.util.HashMap<>();
//        HashMap<Object, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            Random ran = new Random();
            HashSet<Integer> hs = new HashSet<>();
            for (; ; ) {
                int tmp = ran.nextInt(1000) + 1;
                hs.add(tmp);
                if (hs.size() == 100) break;
            }

            for (Integer h : hs) {
                map.put(new Key(h), h);
            }

            for (Integer h : hs) {
                map.remove(new Key(h));
            }

            Asserts.test(map.size() == 0);
        }
    }
}
