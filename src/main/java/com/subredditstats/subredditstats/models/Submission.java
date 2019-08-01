package com.subredditstats.subredditstats.models;

public class Submission {

    private long createdUtc;
    private String subreddit;

    public Submission(long createdUtc, String subreddit) {
        this.createdUtc = createdUtc;
        this.subreddit = subreddit;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }
}
