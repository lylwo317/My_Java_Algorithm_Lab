package com.kevin.datastructures.union;

import com.kevin.datastructures.Asserts;
import com.kevin.utils.Times;

/**
 * Created by: kevin
 * Date: 2021-03-01
 */
public class Main {
	private static final int count = 1000000;
    public static void main(String[] args) {
//		UnionFind uf = new UnionFind_QF(12);
//		UnionFind uf = new UnionFind_QU(12);
//		test(new UnionFind_QF(12));
//		test(new UnionFind_QU(12));
//		test(new UnionFind_QU_Size(12));
//		test(new UnionFind_QU_Rank(12));
//		test(new UnionFind_QU_Rank_PathCompress(12));
//		test(new UnionFind_QU_Rank_PathHalving(12));
		test(new UnionFind_QU_Rank_PathSplitting(12));
		test(new GenericUnionFind<>());

//		testTime(new UnionFind_QF(count));
//		testTime(new UnionFind_QU(count));
//		testTime(new UnionFind_QU_Size(count));
//		testTime(new UnionFind_QU_Rank(count));
//		testTime(new UnionFind_QU_Rank_PathCompress(count));
//		testTime(new UnionFind_QU_Rank_PathHalving(count));
		testTime(new UnionFind_QU_Rank_PathSplitting(count));
		testTime(new GenericUnionFind<>());

		testGenericUnionFind();
    }

    private static void testGenericUnionFind(){
		GenericUnionFind<Student> uf = new GenericUnionFind<>();
		Student stu1 = new Student(1, "jack");
		Student stu2 = new Student(2, "rose");
		Student stu3 = new Student(3, "jack");
		Student stu4 = new Student(4, "rose");
		uf.makeSet(stu1);
		uf.makeSet(stu2);
		uf.makeSet(stu3);
		uf.makeSet(stu4);

		uf.union(stu1, stu2);
		uf.union(stu3, stu4);

//		uf.union(stu1, stu4);

//		Asserts.test(uf.isSame(stu2, stu3));
		Asserts.test(uf.isSame(stu3, stu4));
		Asserts.test(!uf.isSame(stu1, stu3));
	}

	private static void test(UnionFind uf) {
		uf.union(0, 1);
		uf.union(0, 3);
		uf.union(0, 4);
		uf.union(2, 3);
		uf.union(2, 5);

		uf.union(6, 7);

		uf.union(8, 10);
		uf.union(9, 10);
		uf.union(9, 11);

		Asserts.test(!uf.isSame(2, 7));

		uf.union(4, 6);

		Asserts.test(uf.isSame(2, 7));
	}

	private static void test(GenericUnionFind<Integer> uf) {
		for (int i = 0; i < 12; i++) {
			uf.makeSet(i);
		}
		uf.union(0, 1);
		uf.union(0, 3);
		uf.union(0, 4);
		uf.union(2, 3);
		uf.union(2, 5);

		uf.union(6, 7);

		uf.union(8, 10);
		uf.union(9, 10);
		uf.union(9, 11);

		Asserts.test(!uf.isSame(2, 7));

		uf.union(4, 6);

		Asserts.test(uf.isSame(2, 7));
	}

	private static void testTime(GenericUnionFind<Integer> uf) {
		for (int i = 0; i < count; i++) {
			uf.makeSet(i);
		}
		Times.test(uf.getClass().getSimpleName(), () -> {
			for (int i = 0; i < count; i++) {
				uf.union((int)(Math.random() * count),
						(int)(Math.random() * count));
			}

			for (int i = 0; i < count; i++) {
				uf.isSame((int)(Math.random() * count),
						(int)(Math.random() * count));
			}
		});
	}

	private static void testTime(UnionFind uf) {
		Times.test(uf.getClass().getSimpleName(), () -> {
			for (int i = 0; i < count; i++) {
				uf.union((int)(Math.random() * count),
						(int)(Math.random() * count));
			}

			for (int i = 0; i < count; i++) {
				uf.isSame((int)(Math.random() * count),
						(int)(Math.random() * count));
			}
		});
	}
}
