package com.kevin.datastructures.queue;

public class Main {
    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        CircleQueue<Integer> queue = new CircleQueue<Integer>();
        // 0 1 2 3 4 5 6 7 8 9
        for (int i = 0; i < 10; i++) {
            queue.enQueue(i);
        }
        System.out.println(queue);
        // null null null null null 5 6 7 8 9
        for (int i = 0; i < 5; i++) {
            queue.deQueue();
        }
        System.out.println(queue);
        // 15 16 17 18 19 5 6 7 8 9
        for (int i = 15; i < 20; i++) {
            queue.enQueue(i);
        }
        queue.enQueue(100);
        System.out.println(queue);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
        System.out.println(queue);
    }

}
