package com.kevin.sort;

/**
 * 计数排序
 *
 *
 * 时间复杂度：
 * T(n) = O(4n) + O(k) = O(n + k)
 * k是整数取值范围
 *
 * 空间复杂度：O(n+k)，k是整数取值范围
 *
 * Created by: kevin
 * Date: 2021-03-01
 */
public class CountingSort extends Sort<Integer> {
    @Override
    protected void sort() {
        int max = array[0];
        int min = array[0];
        for (int i = 0; i < array.length; i++) {//O(n)
            if (max < array[i]) {
                max = array[i];
            }

            if (min > array[i]) {
                min = array[i];
            }
        }

        int[] counts = new int[max - min + 1];
        //统计
        for (int i = 0; i < array.length; i++) {//O(n)
            counts[array[i] - min]++;
        }
        //累加次数
        for (int i = 1; i < counts.length; i++) {//O(k)
            counts[i] = counts[i] + counts[i - 1];
        }

        //排序
        Integer[] newArray = new Integer[array.length];
        int index;
        for (int i = array.length - 1; i >= 0; i--) {//O(n)
//            index = counts[array[i] - min] - 1;
//            newArray[index] = array[i];
//            counts[array[i] - min] = index;

            newArray[--counts[array[i] - min]] = array[i];
        }

        // 将有序数组赋值到array
//        array = newArray;
        for (int i = 0; i < newArray.length; i++) {//O(n)
            array[i] = newArray[i];
        }
    }

    public static void main(String[] args) {
		Person[] persons = new Person[] {
				new Person(20, "A"),
				new Person(-13, "B"),
				new Person(17, "C"),
				new Person(12, "D"),
				new Person(-13, "E"),
				new Person(20, "F")
		};

		// 找出最值
		int max = persons[0].age;
		int min = persons[0].age;
		for (int i = 1; i < persons.length; i++) {
			if (persons[i].age > max) {
				max = persons[i].age;
			}
			if (persons[i].age < min) {
				min = persons[i].age;
			}
		}

		// 开辟内存空间，存储次数
		int[] counts = new int[max - min + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < persons.length; i++) {
			counts[persons[i].age - min]++;
		}
		// 累加次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}

		// 从后往前遍历元素，将它放到有序数组中的合适位置
		Person[] newArray = new Person[persons.length];
		for (int i = persons.length - 1; i >= 0; i--) {
			newArray[--counts[persons[i].age - min]] = persons[i];
		}

		// 将有序数组赋值到array
		for (int i = 0; i < newArray.length; i++) {
			persons[i] = newArray[i];
		}

		for (int i = 0; i < persons.length; i++) {
			System.out.println(persons[i]);
		}
	}

    private static class Person {
        int age;
        String name;
        Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
        @Override
        public String toString() {
            return "Person [age=" + age
                    + ", name=" + name + "]";
        }
    }
}
