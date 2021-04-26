package leetcode.editor.cn;
//传送带上的包裹必须在 D 天内从一个港口运送到另一个港口。 
//
// 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。 
//
// 返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力。 
//
// 
//
// 示例 1： 
//
// 输入：weights = [1,2,3,4,5,6,7,8,9,10], D = 5
//输出：15
//解释：
//船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
//第 1 天：1, 2, 3, 4, 5
//第 2 天：6, 7
//第 3 天：8
//第 4 天：9
//第 5 天：10
//
//请注意，货物必须按照给定的顺序装运，因此使用载重能力为 14 的船舶并将包装分成 (2, 3, 4, 5), (1, 6, 7), (8), (9), (1
//0) 是不允许的。 
// 
//
// 示例 2： 
//
// 输入：weights = [3,2,2,4,1,4], D = 3
//输出：6
//解释：
//船舶最低载重 6 就能够在 3 天内送达所有包裹，如下所示：
//第 1 天：3, 2
//第 2 天：2, 4
//第 3 天：1, 4
// 
//
// 示例 3： 
//
// 输入：weights = [1,2,3,1,1], D = 4
//输出：3
//解释：
//第 1 天：1
//第 2 天：2
//第 3 天：3
//第 4 天：1, 1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= D <= weights.length <= 50000 
// 1 <= weights[i] <= 500 
// 
// Related Topics 数组 二分查找 
// 👍 232 👎 0

//https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/

import java.util.Arrays;
import java.util.OptionalInt;

class _1011_在_D_天内送达包裹的能力{
    Solution solution = new Solution();
//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 类似于在一个范围内猜数字。这个时候用二分搜索的方式就能快速找到合适点
     *
     * 首先确定范围，船的最小容量 l >= max(weights)，船的最大容量 r <= sum(weights)
     * 然后就在[l, r]范围内找到刚好能满足D天内送达包裹的船的容量
     * @param weights
     * @param D
     * @return
     */
    public int shipWithinDays(int[] weights, int D) {
        int l = Arrays.stream(weights).max().getAsInt();
        int r = Arrays.stream(weights).sum();
        while (l < r) {
            int mid = (l + r) >>> 1;
            int cur = 0;
            int day = 1;
            for (int weight : weights) {
                if (cur + weight > mid) {
                    day++;
                    cur = 0;
                }
                cur += weight;
            }

            if (day <= D) {//船的容量比需要的最小值大，缩小右边
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

    public static void main(String[] args) {
        _1011_在_D_天内送达包裹的能力 problem = new _1011_在_D_天内送达包裹的能力();
    }
}