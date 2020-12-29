package com.kevin.datastructures.set;

public class Main {
    private static void testListSet() {
        testSet(new ListSet<>());
    }

    private static void testTreeSet() {
        testSet(new TreeSet<>());
    }

    private static void testSet(Set<Integer> integerSet) {
        integerSet.add(1);
        integerSet.add(4);
        integerSet.add(2);
        integerSet.add(2);
        integerSet.add(3);
        integerSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void main(String[] args) {
        testListSet();
        System.out.println();
        testTreeSet();
    }
}
