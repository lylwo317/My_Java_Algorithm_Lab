package com.kevin.algorithm.linked_list;

public class ListNode {
    private int val;
    public ListNode next;

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode getNext() {
        return next;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public ListNode(int val) {
        this.val = val;
    }
}
