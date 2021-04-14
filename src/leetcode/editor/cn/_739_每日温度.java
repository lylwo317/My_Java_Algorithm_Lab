package leetcode.editor.cn;
//请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
// 
//
// 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2
//, 1, 1, 0, 0]。 
//
// 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。 
// Related Topics 栈 哈希表 
// 👍 716 👎 0

//https://leetcode-cn.com/problems/daily-temperatures/

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

//class _739_每日温度{

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] dailyTemperatures(int[] t) {

        Deque<Integer> stack = new ArrayDeque<>();
        int[] rMaxDiffArray = new int[t.length];
        for (int i = 0; i < t.length; i++) {
            while (true) {
                Integer top = stack.peek();
                if (top == null || t[top] >= t[i]) {
                    //t[top]是t[i]的左边最大
                    stack.push(i);
                    break;
                } else {
                    //t[i]是t[top]的右边最大
                    rMaxDiffArray[top] = i - top;
                    stack.pop();
                }
            }
        }

        return rMaxDiffArray;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ints = solution.dailyTemperatures(new int[]{89, 62, 70, 58, 47, 47, 46, 76, 100, 70});
        //                                               [0,  1,  2,  3,  4,  5,  6,  7,  8,   9]
        System.out.println(Arrays.toString(ints));
    }
}
//leetcode submit region end(Prohibit modification and deletion)

//}