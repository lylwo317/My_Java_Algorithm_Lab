package com.kevin.datastructures.union;

import java.util.Arrays;

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
public class UnionFind_QU_Rank_PathCompress extends UnionFind_QU_Rank {
    public UnionFind_QU_Rank_PathCompress(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        if (parents[v] != v) {//不是根节点
            parents[v] = find(parents[v]);//返回根节点
        }//单纯只是为了加快查找，所以不用调整rank
        return parents[v];//根节点
    }
}
