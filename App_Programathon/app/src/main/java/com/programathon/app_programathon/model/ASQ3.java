package com.programathon.app_programathon.model;

public class ASQ3 {
    private int id;
    private String name;
    private int minMonths;
    private int maxMonths;
    private int minDays;
    private int maxDays;

    public ASQ3(int id, String name, int minMonths, int maxMonths, int minDays, int maxDays) {
        this.id = id;
        this.name = name;
        this.minMonths = minMonths;
        this.maxMonths = maxMonths;
        this.minDays = minDays;
        this.maxDays = maxDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinMonths() {
        return minMonths;
    }

    public void setMinMonths(int minMonths) {
        this.minMonths = minMonths;
    }

    public int getMaxMonths() {
        return maxMonths;
    }

    public void setMaxMonths(int maxMonths) {
        this.maxMonths = maxMonths;
    }

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
}
