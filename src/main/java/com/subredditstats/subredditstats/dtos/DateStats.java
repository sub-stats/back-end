package com.subredditstats.subredditstats.dtos;

import com.subredditstats.subredditstats.models.DateStat;

import java.util.List;

public class DateStats {
    private List<DateStat> results;

    public DateStats(List<DateStat> results) {
        this.results = results;
    }

    public List<DateStat> getResults() {
        return results;
    }

    public void setResults(List<DateStat> results) {
        this.results = results;
    }

    public void addDateStat(DateStat dateStat) {
        this.results.add(dateStat);
    }
}
