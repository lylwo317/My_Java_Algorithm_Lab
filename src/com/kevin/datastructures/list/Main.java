package com.kevin.datastructures.list;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "");
        }
        System.out.println(list);

        for (int i = 0; i < 5; i++) {
            list.remove(1);
        }
        System.out.println(list);
    }
}
