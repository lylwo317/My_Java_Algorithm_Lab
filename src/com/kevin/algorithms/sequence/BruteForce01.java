package com.kevin.algorithms.sequence;

/**
 * 暴力搜索
 * Created by: kevin
 * Date: 2021-03-14
 */
public class BruteForce01 {
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

        int pIndex = 0, tIndex = 0;
        while (pIndex < pLen && tIndex - pIndex <= tLen - pLen) {
            if (textChars[tIndex] == patternChars[pIndex]) {
                tIndex++;
                pIndex++;
            } else {
                tIndex -= pIndex - 1;
                pIndex = 0;
            }
        }

        return pIndex == pLen ? tIndex - pIndex : -1;
    }
}
