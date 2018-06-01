package com.elad.reddit.service.repository.network;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.elad.reddit.service.model.NetworkState;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.service.repository.network.paging.ItemKeyedRedditListingDataSource;
import com.elad.reddit.service.repository.network.paging.RedditPostDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Elad on 5/31/2018.
 */

public class RedditNetwork {

    final private static String TAG = RedditNetwork.class.getSimpleName();
    final private LiveData redditList;
    final private LiveData<NetworkState> networkState;

    public RedditNetwork(String channelName){
        Executor executor = Executors.newFixedThreadPool(5);
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(25).setPageSize(10).build();
        RedditPostDataSourceFactory dataSourceFactory = new RedditPostDataSourceFactory(executor, channelName);

        networkState = Transformations.switchMap(dataSourceFactory.getMutableLiveData(),
                (Function<ItemKeyedRedditListingDataSource, LiveData<NetworkState>>)
                        ItemKeyedRedditListingDataSource::getNetworkState);
        redditList = (new LivePagedListBuilder(dataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<PagedList<RedditPost>> getRedditList() {

        return redditList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
