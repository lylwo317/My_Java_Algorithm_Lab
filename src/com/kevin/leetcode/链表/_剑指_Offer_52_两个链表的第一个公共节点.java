package com.kevin.leetcode.链表;

public class 剑指_Offer_52_两个链表的第一个公共节点 {

    _剑指_Offer_52_两个链表的第一个公共节点() {
        

    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }

        ListNode aHead = headA;
        ListNode bHead = headB;

        while (aHead != bHead) {
            if (aHead != null) {
                aHead = aHead.next;
            } else {
                aHead = headB;
            }

            if (bHead != null) {
                bHead = bHead.next;
            } else {
                bHead = headA;
            }
        }

        return aHead;
    }
}
