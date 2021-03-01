package com.kevin.datastructures.union;

/**
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public abstract class UnionFind {

    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }

        parents = new int[capacity];
        //初始化并查集
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /*
     * 父节点就是根节点
     */
    public abstract int find(int v);

    protected void rangeCheck(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("v is out of bounds");
        }
    }

    public abstract void union(int v1, int v2);

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

}
