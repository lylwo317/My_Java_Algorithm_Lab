package leetcode.editor.cn;
//请判断一个链表是否为回文链表。 
//
// 示例 1: 
//
// 输入: 1->2
//输出: false 
//
// 示例 2: 
//
// 输入: 1->2->2->1
//输出: true
// 
//
// 进阶： 
//你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？ 
// Related Topics 链表 双指针 
// 👍 900 👎 0

import com.kevin.leetcode.链表.ListNode;

class _234_回文链表{

//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    /**
     * 思路：
     * 找到中点，然后反转右边的链表。然后同步比较
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode mid = getMidNode(head);

        ListNode newHead = reverseList(mid.next);

        boolean isPalindrome = true;
        while (newHead != null && head != null) {
            if (newHead.val != head.val) {
                isPalindrome = false;
                break;
            }

            newHead = newHead.next;
            head = head.next;
        }

        return isPalindrome;
    }

    public boolean isPalindrome1(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode mid = getMidNode(head);

        ListNode newHead = reverseList(mid.next);

        boolean isPalindrome = true;
        while (newHead != null && head != null) {
            if (newHead.val != head.val) {
                isPalindrome = false;
                break;
            }

            newHead = newHead.next;
            head = head.next;
        }

        return isPalindrome;
    }


    /**
     * 使用快慢指针获取链表中间节点
     * 原理：A，B两个人100米冲刺，其中A的速度是v，B的速度是2v。则当B跑到终点时，A才刚到跑50米（中间）
     * @param head
     * @return
     */
    public ListNode getMidNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 反转链表
     * @param head
     */
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }

        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}