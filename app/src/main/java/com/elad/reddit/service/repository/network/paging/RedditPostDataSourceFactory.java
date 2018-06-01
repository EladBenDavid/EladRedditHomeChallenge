package com.elad.reddit.service.repository.network.paging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;


public class RedditPostDataSourceFactory extends DataSource.Factory {

    private String channelName;
    MutableLiveData<ItemKeyedRedditListingDataSource> mutableLiveData;
    ItemKeyedRedditListingDataSource itemKeyedRedditPostDataSource;
    Executor executor;

    public RedditPostDataSourceFactory(Executor executor, String channelName) {
        this.channelName = channelName;
        this.mutableLiveData = new MutableLiveData<>();
        this.executor = executor;
    }


    @Override
    public DataSource create() {
        itemKeyedRedditPostDataSource = new ItemKeyedRedditListingDataSource(executor, channelName);
        mutableLiveData.postValue(itemKeyedRedditPostDataSource);
        return itemKeyedRedditPostDataSource;
    }

    public MutableLiveData<ItemKeyedRedditListingDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}
