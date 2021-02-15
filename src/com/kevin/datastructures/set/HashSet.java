package com.kevin.datastructures.set;

import com.kevin.datastructures.map.HashMap;
import com.kevin.datastructures.map.Map;

/**
 * Created by: kevin
 * Date: 2021-02-15
 */
public class HashSet<E> implements Set<E>{
    private HashMap<E, Object> map;

    public HashSet() {
        this.map = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
        map.put(element, null);//树本身就不会允许comparable相等的两个值
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (map.size() == 0 || visitor == null) {
            return;
        }

        map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                return visitor.visit(key);
            }
        });

    }

}
