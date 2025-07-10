package com.latelier.tenisu_api.model;

import java.util.List;

public class PlayerData {
    private int rank;
    private int points;
    private int weight;
    private int height;
    private int age;
    private List<Integer> last;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Integer> getLast() {
        return last;
    }

    public void setLast(List<Integer> last) {
        this.last = last;
    }
}
