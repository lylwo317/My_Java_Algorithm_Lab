package com.kevin.datastructures.set;

import com.kevin.utils.Times;
import com.kevin.utils.file.FileInfo;
import com.kevin.utils.file.Files;

import java.io.File;

public class Main {
    private static void testListSet() {
        testSet(new ListSet<>());
    }

    private static void testTreeSet() {
        testSet(new TreeSet<>());
    }

    private static void testFileInfo(){
        FileInfo fileInfo = Files.read("/Users/kevinxie/CLionProjects/curl-7.72.0", new String[]{"c"});
        System.out.println("文件数量：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        System.out.println("单词数量：" + fileInfo.words().length);

        System.out.println();

        Times.test("ListSet", () -> testSet2(new ListSet<>(), fileInfo));
        Times.test("TreeSet", () -> testSet2(new TreeSet<>(), fileInfo));
    }

    private static void testSet2(Set<String> stringSet, FileInfo fileInfo) {
        String[] words = fileInfo.words();
        for (int i = 0; i < words.length; i++) {
            stringSet.add(words[i]);
        }
        System.out.println(stringSet.size());
    }

    private static void testSet(Set<Integer> integerSet) {
        integerSet.add(1);
        integerSet.add(4);
        integerSet.add(2);
        integerSet.add(2);
        integerSet.add(3);
        integerSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void main(String[] args) {
        testListSet();
        System.out.println();
        testTreeSet();

        testFileInfo();
    }
}
