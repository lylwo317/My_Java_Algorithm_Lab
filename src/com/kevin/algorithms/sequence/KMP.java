package com.kevin.algorithms.sequence;

import java.util.Arrays;

/**
 * Created by: kevin
 * Date: 2021-03-14
 */
public class KMP {
    /**
     * 配合 https://blog.csdn.net/yyzsir/article/details/89462339  来理解
     *
     * p    = [ D,  B,  C,  D,  E,  D,  B,  C,  E,  A,  B,  C,  D,  E,  A,  B,  C,  D,  F,  B,  G]
     * next = [-1,  0,  0,  0,  1,  0,  1,  2,  3,  0,  0,  0,  0,  1,  0,  0,  0,  0,  1,  0,  0]
     *
     * next 数组的值是除当前字符外（注意不包括当前字符）的公共真前后缀最长长度，相当于将表中公共前后缀最长长度全部右移一位，第一个值赋为-1。
     * @param pattern
     * @return
     */
    private static int[] next(String pattern) {
        char[] p = pattern.toCharArray();
        int[] next = new int[p.length];

        next[0] = -1;
        int j = 0;
        int k = -1;//next数组值
        while (j < p.length - 1) {//整体右移1位，所以不需要计算p.length - 1对应的next
            if (k < 0 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];//往前找更短的公共前后缀
            }
        }
        System.out.println("p    = " + arrayToString(p));
        System.out.println("next = " + arrayToString(next));
        return next;
    }

    /**
     * 优化
     * p    = [ D,  B,  C,  D,  E,  D,  B,  C,  E,  A,  B,  C,  D,  E,  A,  B,  C,  D,  F,  B,  G]
     * next = [-1,  0,  0, -1,  1, -1,  0,  0,  3,  0,  0,  0, -1,  1,  0,  0,  0, -1,  1,  0,  0]
     * @param pattern
     * @return
     */
    private static int[] next2(String pattern) {
        char[] p = pattern.toCharArray();
        int[] next = new int[p.length];

        next[0] = -1;
        int j = 0;
        int k = -1;//next数组值
        while (j < p.length - 1) {//整体右移1位，所以不需要计算p.length - 1对应的next
            if (k < 0 || p[j] == p[k]) {
//                next[++j] = ++k;
//                ++j;
//                ++k;
                if (p[++j] == p[++k]) {
                    // 说明到时候匹配p[j]的时候。
                    // 如果不相等，通过next数组，挪动到p[k]也是不相等的。所以完全可以缩减到p[next[k]]
                    next[j] = next[k];
                } else {
                    next[j] = k;//优化k<0的情况，
                }
            } else {
                k = next[k];//往前找更短的公共前后缀
            }
        }
        System.out.println("p    = " + arrayToString(p));
        System.out.println("next = " + arrayToString(next));

        return next;
    }

    private static String arrayToString(char[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.format("%1$2s", a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
    private static String arrayToString(int[] a) {
        if (a == null)
            return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.format("%1$2d", a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    /**
     * 时间复杂度: O(n)，不超过O(2n)
     * 空间复杂度：O(m) next表
     * @param text
     * @param pattern
     * @return
     */
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null ||
                text.length() == 0 || pattern.length() == 0 ||
                pattern.length() > text.length()) {
            return -1;
        }

        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;

        int[] next = next2(pattern);

        int pIndex = 0, tIndex = 0;
        while (pIndex < pLen && tIndex - pIndex <= tLen - pLen) {
            if (pIndex < 0 || textChars[tIndex] == patternChars[pIndex]) {//next[0] = -1
                tIndex++;
                pIndex++;
            } else {
//                tIndex = tIndex - pIndex + 1;
//                pIndex = 0;
                pIndex = next[pIndex];
            }
        }

        return pIndex == pLen ? tIndex - pIndex : -1;
    }
}
