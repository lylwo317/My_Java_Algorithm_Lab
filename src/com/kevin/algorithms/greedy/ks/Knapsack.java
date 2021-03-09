package com.kevin.algorithms.greedy.ks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 0-1背包问题
 * 使用贪心来解决
 *
 *
 */
public class Knapsack {

	public static void main(String[] args) {
		select("价值优先", (o1, o2) -> o2.getValue() - o1.getValue());
		select("重量优先", (o1, o2) -> o1.getWeight() - o2.getWeight());
		select("性价比优先", (o1, o2) -> Double.compare(o2.getValueDensity(), o1.getValueDensity()));
	}

    private static void select(String title, Comparator<Goods> goodsComparator) {
		Goods[] goods = new Goods[] {
				new Goods(35, 10), new Goods(30, 40),
				new Goods(60, 30), new Goods(50, 50),
				new Goods(40, 35), new Goods(10, 40),
				new Goods(25, 30)
		};

		Arrays.sort(goods, goodsComparator);

		int capacity = 150, weight = 0, value = 0;
		List<Goods> selectedGoods = new ArrayList<>();
		for (Goods good : goods) {
			if (good.getWeight() + weight <= capacity) {
				weight += good.getWeight();
				value += good.getValue();
				selectedGoods.add(good);
			}
		}

		System.out.println(title);
		System.out.println("All value = " + value);
		for (Goods selectedGood : selectedGoods) {
			System.out.println(selectedGood);
		}
		System.out.println();
		System.out.println();
    }
}
