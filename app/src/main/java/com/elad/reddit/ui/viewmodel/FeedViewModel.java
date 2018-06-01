package com.elad.reddit.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.content.Context;

import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.service.repository.Repository;
import com.elad.reddit.service.model.NetworkState;
import com.elad.reddit.ui.view.WebActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Elad on 5/28/2018.
 */

public class FeedViewModel extends ViewModel {
    private static final String TAG = FeedViewModel.class.getSimpleName();
    private Repository repository;

    public void initRepository(Context context, String channelName) throws IOException {
       repository = Repository.getInstance(context.getApplicationContext(), channelName);
    }

    public LiveData<PagedList<RedditPost>> getRedditList() {
        return repository.getRedditList();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

    public void setNewChannel(String channelName) {
        repository.setNewChannel(channelName);
    }

    public void addItemToStorage(RedditPost redditPost, Context context) throws IOException, JSONException {
        repository.addItemToStorage(redditPost, context);
    }

    public List<RedditPost> getSavedPosts() throws IOException {
        return repository.getSavedPosts();
    }

    public List<RedditPost> startQuery(String query) throws IOException {
        return repository.startQuery(query);
    }
}
