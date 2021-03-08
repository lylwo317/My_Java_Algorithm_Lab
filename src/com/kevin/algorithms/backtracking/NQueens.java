package com.kevin.algorithms.backtracking;

/**
 * N皇后问题
 *
 * 时间复杂度：
 * 优化思路：查表来获取当前位置是否可以摆放，避免每次遍历前面的行的确定是否可以摆放
 *
 * 空间复杂度:
 * 优化思路：使用二进制存储要拿来查询的表
 *
 * Created by: kevin
 * Date: 2021-03-07
 */
public class NQueens {
    
    public static void main(String[] args) {
        NQueens nQueens = new NQueens(4);
        nQueens.place();
        System.out.println(nQueens.cols.length + "皇后问题，一共有" + nQueens.ways + "种摆法");
    }
    
    int[] cols;
    int rows;
    int ways;

    public NQueens(int num) {
        cols = new int[num];
        rows = num;
    }

    public void place() {
        place(0);
    }

    /**
     * 遍历行
     * @param row 当前遍历的行
     */
    private void place(int row) {
        if (row == cols.length) {
            show();
            ways++;
            return;
        }
        for (int col = 0; col < cols.length; col++) {//遍历每一列
            if (isValid(row, col)) {//这个位置有效，就继续往下一行遍历
                cols[row] = col;//放置在[row, col]位置
                place(row + 1);//遍历下一行
            }
        }
    }

    private boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col) {
                return false;
            }

            if (row - i == Math.abs(cols[i] - col)) {
                return false;
            }
        }
        return true;
    }

    private void show() {
        for (int row : cols) {
            for (int col = 0; col < cols.length; col++) {
                if (row == col) {
                    System.out.print(" 1 ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

}
