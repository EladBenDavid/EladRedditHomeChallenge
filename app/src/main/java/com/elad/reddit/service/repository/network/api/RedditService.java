package com.elad.reddit.service.repository.network.api;

import com.elad.reddit.service.model.RedditPost;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RedditService {

    @GET()
    Call<ArrayList<RedditPost>> getRedditListing(@Url String url, @Query("after") String after);
}
