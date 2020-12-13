package com.kevin.jdk;

import java.util.*;

public class TestMap {
    public static void main(String[] args) {
        //仅仅只是需要建立映射关系，使用这个Map
        Map<String, String> map = new HashMap<>();//是无序的
        map.put("10", "0");
        map.put("11", "1");
        map.put("12", "2");
        map.put("13", "3");
        System.out.println("HashMap");
        printMap(map);

        //需要建立映射关系并且还需要按照插入顺序遍历的，使用这个Map(内部其实是使用HashMap实现）
        map = new LinkedHashMap<>();//相比HashMap多了记录插入顺序的功能
        map.put("10", "0");
        map.put("11", "1");
        map.put("12", "2");
        map.put("13", "3");
        System.out.println("LinkedHashMap");
        printMap(map);

        map = new LinkedHashMap<>(0, 0.75f, true);//根据put，get调整顺序，可以做LRU缓存
        map.put("10", "0");
        map.put("11", "1");
        map.put("12", "2");
        map.put("13", "3");
        map.put("10", "0");
        System.out.println("LinkedHashMap");
        printMap(map);

        //需要建立映射关系并且还需要对key排序的，使用这个Map
        map = new TreeMap<>();//必须要有比较器或者元素实现了Comparable，内部是红黑树实现。所以插入后，遍历出来是按照升序排列的
        map.put("11", "1");
        map.put("13", "3");
        map.put("12", "2");
        map.put("10", "0");
        //10 11 12 13
        System.out.println("TreeMap");
        printMap(map);

        //弃用
        map = new Hashtable<>();//使用链表。hashcode 算出 index就添加到链表，除此之外方法都是synchronous
        map.put("11", "1");
        map.put("13", "3");
        map.put("12", "2");
        map.put("10", "0");
        //10 11 12 13
        System.out.println("TreeMap");
        printMap(map);
    }

    private static void printMap(Map<String, String> map) {
        map.forEach((s, s2) -> System.out.print(s + " "));
        System.out.println();
    }
}
