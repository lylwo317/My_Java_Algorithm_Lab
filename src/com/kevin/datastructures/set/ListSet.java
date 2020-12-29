package com.kevin.datastructures.set;

import com.kevin.datastructures.list.ArrayList;
import com.kevin.datastructures.list.List;

public class ListSet<E> implements Set<E> {

    private List<E> list;

    public ListSet() {
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        //重复就不添加
        int i = list.indexOf(element);
        if (i != List.ELEMENT_NOT_FOUND) {
            list.set(i, element);
        } else {
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        int i = list.indexOf(element);
        if (i != List.ELEMENT_NOT_FOUND) {
            list.remove(i);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (visitor.visit(list.get(i))) {
                return;
            }
        }
    }
}
