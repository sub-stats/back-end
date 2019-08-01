package com.subredditstats.subredditstats.models;

public class FieldSpec {
    private String name;
    private int maxValue;
    private boolean shouldBeInt;

    public FieldSpec(String name, int maxValue, boolean shouldBeInt) {
        this.name = name;
        this.maxValue = maxValue;
        this.shouldBeInt = shouldBeInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isShouldBeInt() {
        return shouldBeInt;
    }

    public void setShouldBeInt(boolean shouldBeInt) {
        this.shouldBeInt = shouldBeInt;
    }
}
