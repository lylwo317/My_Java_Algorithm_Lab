package com.kevin.sort;

import java.text.DecimalFormat;

public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {
    protected T[] array;
    private int cmpCount;
    private int swapCount;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        this.array = array;

        long start = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - start;
    }

    protected abstract void sort();

    protected int compare(int i, int j) {
        cmpCount++;
        return array[i].compareTo(array[j]);
    }

    protected int compare(T v1, T v2) {
        cmpCount++;
        return v1.compareTo(v2);
    }

    protected void swap(int i, int j) {
        swapCount++;
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    /**
     * 对统计结果排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Sort<T> o) {
        if (time != o.time) {
            return (int) (time - o.time);
        } else if (cmpCount != o.cmpCount) {//比较cmpCount
            return cmpCount - o.cmpCount;
        } else {
            return swapCount - o.swapCount;
        }
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

    public boolean isStable() {
        if (this instanceof QuickSort) {
            return false;
        }
        Element[] elements = new Element[100];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new Element(i * 10, 10);
        }
        sort((T[]) elements);
        for (int i = 1; i < elements.length; i++) {
            int id = elements[i].getId();
            int prevId = elements[i - 1].getId();
            if (id != prevId + 10) return false;
        }
        return true;
    }

}
