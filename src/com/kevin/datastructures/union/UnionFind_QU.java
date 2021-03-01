package com.kevin.datastructures.union;

/**
 * 查找时间复杂度
 * O(n) = O(log n)
 *
 * 合并时间复杂度
 * O(n) = O(log n)
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class UnionFind_QU extends UnionFind {

    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
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

        parents[parent1] = parent2;
    }
}
