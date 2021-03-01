package com.kevin.datastructures.union;

import java.util.Arrays;

/**
 * 查找时间复杂度
 * O(n) = O(log n)
 *
 * 合并时间复杂度
 * O(n) = O(log n)
 *
 * 基于rank优化
 *
 * 核心思想是确保树的平衡，通过高度来判断谁嫁接谁
 *
 * 两个一高一矮的树做union操作，谁嫁接谁？
 * 只有矮的嫁接到高的，才能保证高的树高度不变，矮的成为高的树的一个子树
 *
 * 两个高度一样的时候。谁嫁接谁都可以。但是被嫁接的rank（高度）+1
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class UnionFind_QU_Rank extends UnionFind_QU {
    protected int[] ranks;//记录树的高度

    public UnionFind_QU_Rank(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        Arrays.fill(ranks, 1);
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

        if (ranks[parent1] > ranks[parent2]) {
            parents[parent2] = parent1;
        } else if (ranks[parent1] < ranks[parent2]) {
            parents[parent1] = parent2;
        } else {//ranks ==
            parents[parent1] = parent2;
            ranks[parent2] += 1;
        }
    }
}
