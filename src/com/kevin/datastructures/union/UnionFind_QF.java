package com.kevin.datastructures.union;

/**
 * 查找时间复杂度
 * O(n) = O(1)
 *
 * 合并时间复杂度
 * O(n) = O(n)
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class UnionFind_QF extends UnionFind {

    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    /*
     * 父节点就是根节点
     */
    @Override
    public int find(int v) {//O(1)
        rangeCheck(v);
        return parents[v];
    }

    /**
     * 将v1所在集合的所有元素都嫁接到v2的父节点上
     * @param v1
     * @param v2
     */
    @Override
    public void union(int v1, int v2) {
        int parent1 = find(v1);
        int parent2 = find(v2);
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == parent1) {
                parents[i] = parent2;
            }
        }
    }

}
