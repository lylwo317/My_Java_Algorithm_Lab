package com.kevin.algorithms.greedy.ks;

/**
 * 物品
 */
public class Goods {
    private int weight = 0;
    private int value = 0;
    private double valueDensity = 0f;

    public Goods(int weight, int value) {
        this.weight = weight;
        this.value = value;
        this.valueDensity = value * 1.0/weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public double getValueDensity() {
        return valueDensity;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "weight=" + weight +
                ", value=" + value +
                ", valueDensity=" + valueDensity +
                '}';
    }
}
