package com.elad.reddit.service.repository.network.api;


import com.elad.reddit.service.model.RedditPost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditApi {

    public static final Type REDDIT_POST_ARRAY_LIST_CLASS_TYPE = (new ArrayList<RedditPost>()).getClass();
    public static final String BASE_URL = "https://www.reddit.com/";
    public static final String BASE_URL_SUFFIX = "r/";
    public static final String JSON_FORMAT = ".json";

    public static RedditService createRedditService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                // we remove from the response some wrapper tags from our RedditPosts array
                .registerTypeAdapter(REDDIT_POST_ARRAY_LIST_CLASS_TYPE, new RedditPostsJsonDeserializer())
                .create();
        // https://www.reddit.com/top.json?after=
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BASE_URL + BASE_URL_SUFFIX);

        return builder.build().create(RedditService.class);
    }
}
