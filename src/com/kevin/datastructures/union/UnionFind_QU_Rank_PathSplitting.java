package com.kevin.datastructures.union;

/**
 * 查找时间复杂度
 * O(n) = O(log n)
 *
 * 合并时间复杂度
 * O(n) = O(log n)
 *
 * 基于rank优化，并且进行路径压缩
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
public class UnionFind_QU_Rank_PathSplitting extends UnionFind_QU_Rank {
    public UnionFind_QU_Rank_PathSplitting(int capacity) {
        super(capacity);
    }

    /**
     * 路径分裂，相比路径压缩没有那么极致。但是又起到将树的高度降低一半的作用
     * @param v
     * @return
     */
    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {//不是根节点
            int p = parents[v];
            parents[v] = parents[parents[v]];
            v = p;
        }
        return v;//根节点
    }
}
