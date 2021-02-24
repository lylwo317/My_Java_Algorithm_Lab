package com.kevin.sort;

public class Element implements Comparable<Element> {
    private int id;
    private int value;

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public Element(int id, int value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public int compareTo(Element o) {
        return value - o.value;
    }
}
