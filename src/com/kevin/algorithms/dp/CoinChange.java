package com.kevin.algorithms.dp;

public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coins2(41, new int[] {1, 5, 25, 20}));
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
