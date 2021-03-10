package com.kevin.algorithms.dp;

public class CoinChange {
    public static void main(String[] args) {
        System.out.println(coins1(41, new int[] {1, 5, 25, 20}));
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
     * 暴力递归
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
