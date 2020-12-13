package com.kevin.jdk;

import sun.misc.SharedSecrets;

import java.time.DayOfWeek;
import java.util.EnumSet;

public class TestEnumSet {

    /*
     输出结果
        MONDAY
        TUESDAY
        WEDNESDAY
        THURSDAY
        FRIDAY
        SATURDAY
        SUNDAY

        MONDAY
        FRIDAY
     */
    /**
     * 里面使用的是bit来表示某一个Enum是否添加到set了
     */
    public static void main(String[] args) {
        // 返回EnumSet
        EnumSet<DayOfWeek> allSet = EnumSet.allOf(DayOfWeek.class);
        for(DayOfWeek each : allSet)
        {
            System.out.println(each);
        }

        System.out.println();

        EnumSet<DayOfWeek> enumSet = EnumSet.noneOf(DayOfWeek.class);
//        SharedSecrets.getJavaLangAccess()
//                .getEnumConstantsShared(DayOfWeek.class);

        enumSet.add(DayOfWeek.MONDAY);
        enumSet.add(DayOfWeek.MONDAY);
        enumSet.add(DayOfWeek.FRIDAY);
        enumSet.add(DayOfWeek.MONDAY);

        for(DayOfWeek each : enumSet)
        {
            System.out.println(each);
        }

    }
}
