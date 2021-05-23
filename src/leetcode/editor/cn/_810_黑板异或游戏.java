package leetcode.editor.cn;
//黑板上写着一个非负整数数组 nums[i] 。Alice 和 Bob 轮流从黑板上擦掉一个数字，Alice 先手。如果擦除一个数字后，剩余的所有数字按位异或
//运算得出的结果等于 0 的话，当前玩家游戏失败。 (另外，如果只剩一个数字，按位异或运算得到它本身；如果无数字剩余，按位异或运算结果为 0。） 
//
// 并且，轮到某个玩家时，如果当前黑板上所有数字按位异或运算结果等于 0，这个玩家获胜。 
//
// 假设两个玩家每步都使用最优解，当且仅当 Alice 获胜时返回 true。 
//
// 
//
// 示例： 
//
// 
//输入: nums = [1, 1, 2]
//输出: false
//解释: 
//Alice 有两个选择: 擦掉数字 1 或 2。
//如果擦掉 1, 数组变成 [1, 2]。剩余数字按位异或得到 1 XOR 2 = 3。那么 Bob 可以擦掉任意数字，因为 Alice 会成为擦掉最后一个数
//字的人，她总是会输。
//如果 Alice 擦掉 2，那么数组变成[1, 1]。剩余数字按位异或得到 1 XOR 1 = 0。Alice 仍然会输掉游戏。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= N <= 1000 
// 0 <= nums[i] <= 2^16 
// 
// Related Topics 数学 
// 👍 105 👎 0

//https://leetcode-cn.com/problems/chalkboard-xor-game/

class _810_黑板异或游戏{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 这是一道博弈论问题。其实输赢在一开始就定下来了。
     *
     * 设数组nums的长度为n，用⊕表示异或，记S为数组nums的全部元素的异或结果，则有
     * S = nums[0] ⊕ nums[1] ⊕ ... ⊕ nums[n-1]
     *
     * 1. 初始数组nums的异或S = 0，那么先手就赢了
     *
     * 2. 初始化数组nums的异或S ≠ 0，那么情况又是怎么样的？
     * 首先两个人每次都擦除一个数字，就说明了每个人要处理的数组的奇偶性都是固定不变的
     *
     * 偶数方如果总能找到一个数擦除后， 使得剩余数组的异或S≠0，那么他就赢了。
     * 因为擦除最后一个数字的是奇数方，当最后一个数字擦除后。偶数方就赢了，因为偶数方总能找到一个数字擦除后，剩余的异或数组的S≠0
     *
     * 那么接下来就是要证明偶数方是否总能找到一个数字擦除后不为0。
     *
     * 其实可以反过来想，是否可以任选一个数字擦除，使得剩余S＝0。
     * 如果这个不成立，就证明了必然存在一个数字擦除后使得S≠0
     *
     * 假设擦除任意一个数字后，使得S = 0
     * 首先假设擦除数字nums[i]后，则有
     * Si + nums[i] = S
     *
     * Si = S ⊕ nums[i]
     *
     * 如果无论擦掉哪个数字，剩余所有数字异或结果都等于0，即对任意0<= i < n，都有Si = 0。因此对所有Si的异或结果也等于0，即
     * S0 ⊕ S1 ⊕ ... ⊕ Sn-1 = 0
     *
     * 将Si = S ⊕ nums[i] 代入上式，并根据异或运算的交换律和结合律化简，有
     *
     * 0 = S0 ⊕ S1 ⊕ ... ⊕ Sn-1
     *   = (S ⊕ S ⊕ ... ⊕ S) ⊕ (nums[0] ⊕ nums[1] ⊕ ... ⊕ nums[n -1]
     *   = 0 ⊕ S
     *   = S
     *
     * 根据上述计算，可以得到S = 0，与实际情况S≠0矛盾。
     * 因此当数组的长度是偶数是，必然存在一个数字擦掉后，等于所有数字的异或值不等于0
     * @param nums
     * @return
     */
    public boolean xorGame(int[] nums) {
        //偶数总能找到一个数字擦除后，剩余所有元素异或结果不为0，所以这种情况必胜
        if (nums.length % 2 == 0) {
            return true;
        }

        //初始数组的所有元素异或结果
        int S = 0;
        for (int num : nums) {
            S ^= num;
        }
        return S == 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _810_黑板异或游戏 problem = new _810_黑板异或游戏();
    }
}