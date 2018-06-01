package com.elad.reddit.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elad.reddit.service.model.NetworkState;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.service.repository.network.api.RedditApi;
import com.elad.reddit.service.repository.network.api.RedditService;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elad on 5/30/2018.
 */

public class ItemKeyedRedditListingDataSource extends ItemKeyedDataSource<String, RedditPost> {

    public static final String TAG = ItemKeyedRedditListingDataSource.class.getSimpleName();
    RedditService redditService;
    LoadInitialParams<String> initialParams;
    LoadParams<String> afterParams;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;
    private String channelName;
    public ItemKeyedRedditListingDataSource(Executor retryExecutor, String channelName) {
        redditService = RedditApi.createRedditService();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
        this.channelName = channelName + RedditApi.JSON_FORMAT;
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<RedditPost> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        initialParams = params;
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        redditService.getRedditListing(channelName, "").enqueue(new Callback<ArrayList<RedditPost>>() {
            @Override
            public void onResponse(Call<ArrayList<RedditPost>> call, Response<ArrayList<RedditPost>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    callback.onResult(response.body());
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                    initialParams = null;
                } else {
                    Log.e("API CALL", response.message());
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RedditPost>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<RedditPost> callback) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        afterParams = params;

        networkState.postValue(NetworkState.LOADING);
        redditService.getRedditListing(channelName, params.key).enqueue(new Callback<ArrayList<RedditPost>>() {
            @Override
            public void onResponse(Call<ArrayList<RedditPost>> call, Response<ArrayList<RedditPost>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body());
                    networkState.postValue(NetworkState.LOADED);
                    afterParams = null;
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RedditPost>> call, Throwable t) {
                String errorMessage = t.getMessage();
                if (errorMessage == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<RedditPost> callback) {

    }

    @NonNull
    @Override
    public String getKey(@NonNull RedditPost item) {
        return item.getName();
    }

}
