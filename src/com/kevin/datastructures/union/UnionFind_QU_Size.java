package com.kevin.datastructures.union;

import java.util.Arrays;

/**
 * 查找时间复杂度
 * O(n) = O(log n)
 *
 * 合并时间复杂度
 * O(n) = O(log n)
 *
 * 基于size优化
 *
 * 将size小的嫁接到size大的。问题是size大的高度不一定高，所以不如基于rank优化的
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class UnionFind_QU_Size extends UnionFind_QU {
    private int[] sizes;

    public UnionFind_QU_Size(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        Arrays.fill(sizes, 1);
    }

    /**
     * 将v1的根节点嫁接到v2的根节点上
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int parent1 = find(v1);
        int parent2 = find(v2);
        if (parent1 == parent2) {
            return;
        }

        if (sizes[parent1] > sizes[parent2]) {
            parents[parent2] = parent1;
            sizes[parent1] += sizes[parent2];
        } else {
            parents[parent1] = parent2;
            sizes[parent2] += sizes[parent1];
        }

    }
}
