package com.kevin.datastructures.list;

import com.kevin.datastructures.Asserts;
import com.kevin.datastructures.list.circle.CircleLinkedList;
import com.kevin.datastructures.list.singlelinked.SingleLinkedList2;

public class Main {
    public static void main(String[] args) {
//        testArrayList();
//        testLinkedList();
        testList(new LinkedList<>());
        testList(new CircleLinkedList<>());
        josephus();
    }

    //约瑟夫问题
    private static void josephus() {
        CircleLinkedList<Integer> list = new CircleLinkedList<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }

        // 指向头结点（指向1）
        list.reset();

        while (!list.isEmpty()) {
            list.next();
            list.next();
            System.out.println(list.remove());
        }
    }

    private static void testLinkedList() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        Asserts.test(list.size() == 1);
        Asserts.test(list.remove(0) == 1);
        Asserts.test(list.size() == 0);

        list.add(1);
        list.add(54);
        list.add(29);
        list.add(193);

        Asserts.test(list.contains(54));
        Asserts.test(!list.contains(55));
        Asserts.test(list.size() == 4);
        Asserts.test(list.get(3) == 193);
        Asserts.test(list.remove(list.indexOf(54)) == 54);
        Asserts.test(list.size() == 3);
    }

    private static void testArrayList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i + "");
        }
        System.out.println(list);

        for (int i = 0; i < 5; i++) {
            list.remove(0);
        }
        System.out.println(list);

        List<String> list2 = new SingleLinkedList2<>();
        list2.add("10");
        list2.add("11");
        list2.add("13");
        list2.add("14");
        list2.remove(2);
        list2.add(0,"9");
        list2.add(list2.size(),"15");
        System.out.println(list2);

        list2.clear();
        list2.add("100");
        System.out.println(list2);
    }

    static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]

        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]
        list.remove(list.size() - 1); // [11, 66, 33, 44]

        Asserts.test(list.indexOf(44) == 3);
        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
        Asserts.test(list.contains(33));
        Asserts.test(list.get(0) == 11);
        Asserts.test(list.get(1) == 66);
        Asserts.test(list.get(list.size() - 1) == 44);

        System.out.println(list);
    }

}
