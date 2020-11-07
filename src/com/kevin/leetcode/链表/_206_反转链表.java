package com.kevin.leetcode.链表;

public class _206_反转链表 {



    /**
     * 使用递归
     * @param head
     * @return
     */
/*
    public ListNode reverseList(ListNode head) {
        if( head == null || head.next == null){
            return head;
        }

        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }
*/

    /**
     * 使用迭代
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.next;
            current.next = pre;

            pre = current;
            current = next;
        }

        return pre;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(7);
        head.next.next.next = new ListNode(9);

        printListNode(head);

        ListNode newHead = reverseList(head);

        printListNode(newHead);

    }

    private static void printListNode(ListNode head) {
        ListNode iterator = head;
        System.out.print("[");
        while (iterator != null) {
            System.out.print(iterator);
            iterator = iterator.next;
        }
        System.out.println("]");
    }
}
