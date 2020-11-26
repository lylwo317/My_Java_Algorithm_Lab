package com.kevin.datastructures.list;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "");
        }
        System.out.println(list);

        for (int i = 0; i < 5; i++) {
            list.remove(0);
        }
        System.out.println(list);

        List<String> list2 = new LinkedList<>();
        list2.add("10");
        list2.add("11");
        list2.add("13");
        list2.add("14");
        list2.remove(2);
        list2.add(0,"9");
        list2.add(list2.size(),"15");
        System.out.println(list2);
    }
}
