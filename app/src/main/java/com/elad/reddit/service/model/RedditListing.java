package com.elad.reddit.service.model;

import java.util.List;


public class RedditListing {
    private List<RedditPost> children;
    private String before;
    private String after;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<RedditPost> getChildren() {
        return children;
    }
}
