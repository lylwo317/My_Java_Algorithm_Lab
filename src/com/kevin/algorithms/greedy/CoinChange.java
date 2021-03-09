package com.kevin.algorithms.greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 硬币兑换问题
 */
public class CoinChange {
    public static void main(String[] args) {
        coinChange(new Integer[] {25, 20, 5, 1}, 41);
    }

    private static void coinChange(Integer[] faces, int money) {
        Arrays.sort(faces);
        int sum = 0;
        int i = faces.length - 1;
        List<Integer> selectedCoins = new ArrayList<>();
        while (sum < money && i >= 0) {
            if (faces[i] + sum <= money) {
                sum += faces[i];
                selectedCoins.add(faces[i]);
            } else {
                i--;
            }
        }

        System.out.println("兑换方案：" + selectedCoins);
        System.out.println("需要的硬币数量：" + selectedCoins.size());
    }
}
