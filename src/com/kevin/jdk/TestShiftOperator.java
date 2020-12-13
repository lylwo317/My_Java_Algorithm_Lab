package com.kevin.jdk;

import java.util.BitSet;

public class TestShiftOperator {
    /**
     * 左移， 右移， 无符号右移
     * @param args
     */
    public static void main(String[] args) {
        int a = 2;
        System.out.println(paddedWithZero(1438684509));
        //右移负数，等于 32 + 负数
        System.out.println(paddedWithZero(1438684509 >>> -10));
        //效果同上
        System.out.println(paddedWithZero(1438684509 >>> (32-10)));
    }

    public static String paddedWithZero(int i) {
        return String.format("%32s", Integer.toBinaryString(i))
                .replace(' ', '0');
    }
}
