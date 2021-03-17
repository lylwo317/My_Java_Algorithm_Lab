package leetcode.editor.cn;
//给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。注意：n-m尽量最小，也就是说，找出符合条件的最短
//序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。 
// 示例： 
// 输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
//输出： [3,9]
// 
// 提示： 
// 
// 0 <= len(array) <= 1000000 
// 
// Related Topics 排序 数组 
// 👍 64 👎 0

class _面试题_16_16_部分排序{

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 思路：找逆序对，也就是找左边最后一个逆序对的最大值，右边最后一个逆序对的最小值
     * @param array
     * @return
     */
    public int[] subSort(int[] array) {
        if (array.length == 0) return new int[]{-1,-1};

        int l = -1;
        int r = -1;

        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            max = Math.max(max, array[i]);
            if (array[i] < max) {
                r = i;
            }
        }

        int min = array[array.length - 1];
        for (int i = array.length - 1; i >= 0; i--) {
            min = Math.min(min, array[i]);
            if (array[i] > min) {
                l = i;
            }
        }

        return new int[]{l, r};
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}