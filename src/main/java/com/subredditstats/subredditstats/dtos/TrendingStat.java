package com.subredditstats.subredditstats.dtos;

public class TrendingStat {
    private String subredditName;
    private int numberOfFollowers;
    private int ageOfSubInDays;

    public TrendingStat(String subredditName, int numberOfFollowers, int ageOfSubInDays) {
        this.subredditName = subredditName;
        this.numberOfFollowers = numberOfFollowers;
        this.ageOfSubInDays = ageOfSubInDays;
    }

    public String getSubredditName() {
        return subredditName;
    }

    public void setSubredditName(String subredditName) {
        this.subredditName = subredditName;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getAgeOfSubInDays() {
        return ageOfSubInDays;
    }

    public void setAgeOfSubInDays(int ageOfSubInDays) {
        this.ageOfSubInDays = ageOfSubInDays;
    }

    public double getTrendingScore() {
        // Multiplied by 1.0 to force ints to become double
        return this.numberOfFollowers / (this.ageOfSubInDays * 1.0);
    }
}
