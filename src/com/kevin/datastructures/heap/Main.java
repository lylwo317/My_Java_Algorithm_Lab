package com.kevin.datastructures.heap;

import com.kevin.datastructures.tree.printer.BinaryTreeInfo;
import com.kevin.datastructures.tree.printer.BinaryTrees;

import java.util.Comparator;

/**
 * Created by: kevin
 * Date: 2021-02-16
 */
public class Main {
    private static void testHeap() {
        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>();

        binaryHeap.add(34);
        binaryHeap.add(12);
        binaryHeap.add(606);
        binaryHeap.add(23);
        binaryHeap.add(324);
        binaryHeap.add(223);

        BinaryTrees.println(binaryHeap);
        System.out.println("remove");
        for (int i = 0; i < 6; i++) {
            binaryHeap.remove();
            BinaryTrees.println(binaryHeap);
        }

        binaryHeap.replace(123);
        BinaryTrees.println(binaryHeap);
        binaryHeap.add(606);
        binaryHeap.add(23);
        binaryHeap.add(324);
        BinaryTrees.println(binaryHeap);
        binaryHeap.replace(230);
        BinaryTrees.println(binaryHeap);

    }

    private static void testHeapBuild() {

        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(data);
        BinaryTrees.println(binaryHeap);

    }

    /**
     * 核心思想就是。
     * 用size为k的最小堆存下当前遍历过的节点最大的k个值。为什么是用最小堆？你想一下如果你要存最大的k个值。其中可能被替换的肯定是这k个值中的最小值。
     * 因为最小值比新值小，说明这个最小值肯定不是前k个最大值。
     */
    private static void testTopK() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<Integer>(Comparator.reverseOrder());

        int k = 3;

        //算法复杂度计算
        for (int i = 0; i < data.length; i++) {
            if (binaryHeap.size <= k) {
                binaryHeap.add(data[i]);//堆添加复杂度O(logk)
            } else if(binaryHeap.get() < data[i]) {
                binaryHeap.replace(data[i]);//O(logk)
            }
        }//最坏复杂度，O(nLogK)

        BinaryTrees.println(binaryHeap);
    }

    private static void testMinHeapBuild() {

        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};

        BinaryHeap<Integer> binaryHeap = new BinaryHeap<>(data, Comparator.reverseOrder());
        BinaryTrees.println(binaryHeap);

    }
    public static void main(String[] args) {
//        testHeap();
//        testHeapBuild();
        testTopK();
//        testMinHeapBuild();
    }
}
