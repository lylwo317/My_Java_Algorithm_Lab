package com.kevin.algorithms.dp;

/**
 * 找零钱问题
 */
public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coins3(41, new int[] {1, 5, 25, 20}));
    }

    /**
     * 动态规划/递推，自底向上调用
     *
     * 状态定义：
     * dp[i] 表示筹够i块钱，需要的最小硬币数
     *
     * 初始状态：
     * dp[face[0]] = 1, dp[face[1]] = 1 ...
     *
     * 状态转移方程：
     * dp[i] = min(dp[i-face[0]], dp[i-face[1]], ...) + 1//类似走楼梯
     *
     * @param money
     * @param faces
     * @return
     */
    private static int coins3(int money, int[] faces) {
        int[] selected = new int[money + 1];
        int[] dp = new int[money + 1];
        if (money < 1) {
            return 0;
        }
        //初始
        for (int face : faces) {
            dp[face] = 1;
        }

        for (int i = 1; i <= money; i++) {
            int min = Integer.MAX_VALUE;
            int selectedFace = Integer.MAX_VALUE;
            for (int face : faces) {
                if (i - face < 0) {
                    continue;
                }
                if (dp[i - face] < min) {
                    min = dp[i - face];
                    selectedFace = face;
                }
//                min = Math.min(dp[i - face], min);
            }
            if (min != Integer.MAX_VALUE) {//说明有可兑换的硬币
                dp[i] = min + 1;
                selected[i] = selectedFace;
            }
        }
        int m = money;
        System.out.println(money + " 需要兑换的硬币:");
        while (m > 0) {
            System.out.println(selected[m]);//筹够m块钱的最后一枚硬币的面额
            m -= selected[m];
        }
        System.out.println();
        return dp[money];
    }

    private static int coins2(int money, int[] faces) {
        int[] selected = new int[money + 1];
        int[] dp = new int[money + 1];
        int result = coins2(money, faces, selected, dp);
        int index = money;
        System.out.println(money + " 需要兑换的硬币:");
        while (index > 0) {
            System.out.println(selected[index]);
            index -= selected[index];
        }
        System.out.println();
        return result;
    }

    /**
     * 暴力递归+记忆（自顶向下的调用，避免重复求解子问题）
     * @param money
     * @param faces
     * @param dp
     * @return
     */
    private static int coins2(int money, int[] faces, int[] selected, int[] dp) {
        if (money < 1) {
            return Integer.MAX_VALUE;
        }
        if (dp[money] == 0) {//避免重复求解子问题
            for (int face : faces) {
                if (money == face) {
                    selected[money] = face;
                    dp[money] = 1;
                    return dp[money];
                }
            }

            int min = Integer.MAX_VALUE;
            int selectedFace = 0;
            for (int i = 0; i < faces.length; i++) {
                int c = coins2(money - faces[i], faces, selected, dp);
                if (c < min) {
                    min = c;
                    selectedFace = faces[i];
                }
            }
            selected[money] = selectedFace;
            dp[money] = min + 1;
        }
        return dp[money];
    }

    private static int coins1(int money, int[] faces) {
        int[] selected = new int[money + 1];
        int result = coins1(money, faces, selected);
        int index = money;
        System.out.println(money + " 需要兑换的硬币:");
        while (index > 0) {
            System.out.println(selected[index]);
            index -= selected[index];
        }
        System.out.println();
        return result;
    }

    /**
     * 暴力递归（自顶向下的调用，存在重叠子问题，所以效率比较差）
     * 复杂度：类似斐波那契数
     * @param money
     * @param faces
     * @return
     */
    private static int coins1(int money, int[] faces, int[] selected) {
        if (money < 1) {
            return Integer.MAX_VALUE;
        }

        for (int face : faces) {
            if (money == face) {
                selected[money] = face;
                return 1;
            }
        }

        int min = Integer.MAX_VALUE;
        int selectedFace = 0;
        for (int i = 0; i < faces.length; i++) {
            int c = coins1(money - faces[i], faces, selected);
            if (c < min) {
                min = c;
                selectedFace = faces[i];
            }
        }
        selected[money] = selectedFace;
        return min + 1;
    }
}
