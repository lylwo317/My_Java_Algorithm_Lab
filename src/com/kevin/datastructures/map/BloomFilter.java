package com.kevin.datastructures.map;

/**
 * 布隆过滤器
 *
 * Created by: kevin
 * Date: 2021-03-13
 */
public class BloomFilter<T> {

    /**
     * 二进制向量的长度(一共有多少个二进制位)
     */
    private int bitSize;

    /**
     * 二进制向量
     */
    private long[] bits;

    /**
     * 哈希函数的个数
     */
    private int hashSize;

    /**
     *
     * @param n 数据规模
     * @param p 误判率， 取值范围(0, 1)
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("wrong n or p");
        }

        double ln2 = Math.log(2);//ln2

        // 求出二进制向量的长度
        bitSize = (int) (- (n * Math.log(p)) / (ln2 * ln2));
        // 求出哈希函数的个数
        hashSize = (int) (bitSize * ln2 / n);
        // bits数组的长度
        bits = new long[(int) Math.ceil((float)bitSize / Long.SIZE)];
    }

    /**
     * 添加元素
     * @param value
     * @return
     */
    public boolean put(T value) {
        checkNull(value);

        boolean result = false;

        // 利用value生成2个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }

            int index = combinedHash % bitSize;
            if (set(index)) {
                result = true;
            }
        }

        return result;
    }

    public boolean contains(T value) {
        checkNull(value);

        // 利用value生成2个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }

            int index = combinedHash % bitSize;
            if (!get(index)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置index位置的二进制位的值
     * @return true 表示该为原来的值是 0, false 则表示原来是 1
     */
    private boolean set(int index) {
        long blockValue = bits[index / Long.SIZE];
        int bitValue = 1 << (index % Long.SIZE);
        bits[index / Long.SIZE] = blockValue | bitValue;
        return (blockValue & bitValue) == 0;//原来对应的位是0
    }

    /**
     * 查看index位置的二进制位的值
     * @return true 代表这个位为1, false 代表这个位为0
     */
    private boolean get(int index) {
        long blockValue = bits[index / Long.SIZE];
        return (blockValue & (1 << (index % Long.SIZE))) != 0;
    }

    public void checkNull(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
    }
}
