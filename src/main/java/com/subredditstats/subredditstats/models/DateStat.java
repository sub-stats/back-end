package com.subredditstats.subredditstats.models;

import java.util.Map;

public class DateStat {
    // YYYY-MM-DD
    private String date;
    private Map<String, Double> data;

    public DateStat(String date, Map<String, Double> data) {
        this.date = date;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }
}
