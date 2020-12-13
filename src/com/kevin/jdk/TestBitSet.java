package com.kevin.jdk;

import java.util.Arrays;
import java.util.BitSet;

public class TestBitSet {
    public static void main(String[] args) {
        //
        BitSet set = new BitSet(10); //10 bits set

        //set() 设为true
        set.set(0);
        set.set(1);
        set.set(4);
        System.out.println(set); // 应该是列出值为true的那些位的坐标！

        // 8 bit >> 1 byte,  就是说截取8位，转成byte。 就是0010 0011 >>
        System.out.println(Arrays.toString(set.toByteArray()));

        // 和test()中的set.toByteArray()刚好相反，这里是1 byte >> 8 bits! 需要确认90对应的低8bit 还是高8bit。
        BitSet bitSet = BitSet.valueOf(new byte[]{90, 92, 95, 97});//[97,95,92,90]，一共4个Byte排列
        System.out.println(bitSet); //{1, 3, 4, 6, 10, 11, 12, 14, 16, 17, 18, 19, 20, 22, 24, 29, 30}

        //截取下上面的输出即可知道90对应的是低位还是高位
        BitSet bs = new BitSet(8);//
        bs.set(1);
        bs.set(3);
        bs.set(4);
        bs.set(6);
        System.out.println(Arrays.toString(bs.toByteArray()));//90 - 事实证明是小端？
    }
}
