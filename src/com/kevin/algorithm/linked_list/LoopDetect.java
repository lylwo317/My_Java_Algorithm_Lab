package com.kevin.algorithm.linked_list;

public class LoopDetect {
    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        head.next = new ListNode(8);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = head;
        System.out.println("isExistLoop = " + isExistLoopInList(head));

        // ListNode loopEnterNode = findLoopEnter(head);
        // System.out.println("Enter Val = " + loopEnterNode.getVal());
        // printList(head);
    }

    private static void printList(ListNode head) {

        System.out.print("[");
        while (head != null) {
            System.out.print(head.getVal() + " ");
            head = head.next;
        }
        System.out.print("]");
    }

    private static boolean isExistLoopInList(ListNode head) {
        if (head == null || head.next == null) {
            return false; 
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next.next.next;
            slow = slow.next.next;
            if (fast == slow) {//存在
                return true; 
            }
        }

        return false;
    }

    private static ListNode findLoopEnter(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        ListNode meet = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {//存在
                meet = fast;
                break;
            }
        }

        if (meet != null) {
            ListNode one = head;
            ListNode two = meet;

            while(one != two){
                one = one.next;
                two = two.next;
            }

            return two;
        }


        return null;
    }
}
