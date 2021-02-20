package com.kevin.datastructures.stack;

import com.kevin.datastructures.list.ArrayList;
import com.kevin.datastructures.list.List;

/**
 * 核心就是从队列的一端添加或者删除。所以动态数组，链表都可以完成
 * Created by: kevin
 * Date: 2021-02-20
 */
public class Stack<E> {
    private List<E> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }
}
