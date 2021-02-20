package com.kevin.datastructures.stack;

import com.kevin.datastructures.Asserts;

/**
 * Created by: kevin
 * Date: 2021-02-20
 */
public class Main {
    private static void testStack() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Asserts.test(stack.size() == 5);
        Asserts.test(stack.pop() == 5);
        Asserts.test(stack.pop() == 4);
        Asserts.test(stack.pop() == 3);
        Asserts.test(stack.top() == 2);
        Asserts.test(!stack.isEmpty());
        stack.clear();
        Asserts.test(stack.isEmpty());
    }

    public static void main(String[] args) {
        testStack();
    }
}
