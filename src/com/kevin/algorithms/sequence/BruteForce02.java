package com.kevin.algorithms.sequence;

/**
 * 暴力搜索
 * Created by: kevin
 * Date: 2021-03-14
 */
public class BruteForce02 {
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
        for (; tIndex < tLen - pLen; tIndex++) {
            pIndex = 0;
            while(pIndex < pLen) {
                if (textChars[tIndex + pIndex] == patternChars[pIndex]) {
                    pIndex++;
                } else {
                    break;
                }
            }
            if (pIndex == pLen) {
                return tIndex;
            }
        }
        return -1;
    }
}
