package leetcode.editor.cn;
//输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。 
//
// 
//
// 示例 1： 
//
// 输入：head = [1,3,2]
//输出：[2,3,1] 
//
// 
//
// 限制： 
//
// 0 <= 链表长度 <= 10000 
// Related Topics 链表 
// 👍 141 👎 0

//https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/

import com.kevin.leetcode.链表.ListNode;

import java.util.ArrayDeque;
import java.util.Deque;

class _剑指_Offer_06_从尾到头打印链表{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        Deque<Integer> stack = new ArrayDeque<>();
        while (head != null) {
            stack.push(head.val);
            head = head.next;
        }
        int size = stack.size();
        int[] vals = new int[size];
        for (int i = 0; i < size; i++) {
            vals[i] = stack.pop();
        }
        return vals;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _剑指_Offer_06_从尾到头打印链表 problem = new _剑指_Offer_06_从尾到头打印链表();
    }
}