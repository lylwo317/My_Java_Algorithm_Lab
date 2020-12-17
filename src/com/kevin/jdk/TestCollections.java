package com.kevin.jdk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestCollections {
    public static void main(String[] args) {
//        int[] data = new int[]{3,5,7,1,9,13,11};

        List<Integer> dataList = Arrays.asList(3,5,7,1,9,15,13,11);

        Collections.sort(dataList);
//        dataList.toArray()

        int index = Collections.binarySearch(dataList, 4);
        System.out.println("index = " + index);

        System.out.println(dataList);

//        List<Integer> closestElements = findClosestElements(new int[]{ 1,2,7, 9, 15, 13, 11}, 3, 7);
//        System.out.println(closestElements);
    }

    //比较大小，差值

    public <T> List<? extends Comparable<? super T>> findClosestElements(List<? extends Comparable<? super T>> ret, int num, T target ) {
//        List<Integer> ret = Arrays.stream(arr).boxed().collect(Collectors.toList());
//        Collections.sort(arr);
        int n = ret.size();
        if (ret.get(0).compareTo(target) >= 0) {
            return ret.subList(0, num);
        } else if (ret.get(n - 1).compareTo(target) <= 0) {
            return ret.subList(n - num, n);
        } else {
            int index = Collections.binarySearch(ret, target);
            if (index < 0)
                index = -index - 1;
            int low = Math.max(0, index - num), high = Math.min(ret.size() - 1, index + num - 1);

            while (high - low > num - 1) {
//                if ((target - ret.get(low)) <= (ret.get(high) - target))
//                    high--;
//                else if ((target - ret.get(low)) > (ret.get(high) - target))
//                    low++;
//                else
//                    System.out.println("unhandled case: " + low + " " + high);
            }
            return ret.subList(low, high + 1);
        }
    }
}
