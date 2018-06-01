package com.elad.reddit.service.model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import org.parceler.Parcel;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


// immutable object that represent RedditPost response
@Parcel
public class RedditPost implements Serializable{

    private String title, domain, subreddit, thumbnail, url, author, createdOn, name, permalink;
    private int upvotes, score, numComments;
    // help for converting the class to lucene.document.Document
    public static final List<String> CLASS_MEMBERS_FILED;
    static {
        CLASS_MEMBERS_FILED = Arrays.stream(RedditPost.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }

    // use for ordering the items in view
    public static DiffUtil.ItemCallback<RedditPost> DIFF_CALLBACK = new DiffUtil.ItemCallback<RedditPost>() {
        @Override
        public boolean areItemsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem.title == newItem.title;
        }

        @Override
        public boolean areContentsTheSame(@NonNull RedditPost oldItem, @NonNull RedditPost newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };

    // we assuming the name is unique
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof RedditPost) {
            return ((RedditPost) obj).name.equals(this.name);
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getDomain() {
        return domain;
    }

    public int getNumComments() {
        return numComments;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }
}
