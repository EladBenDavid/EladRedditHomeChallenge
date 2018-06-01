package com.elad.reddit.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.content.Context;

import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.service.repository.network.RedditNetwork;
import com.elad.reddit.service.model.NetworkState;
import com.elad.reddit.service.repository.storge.SavedPostsStorge;
import com.elad.reddit.ui.view.RedditActivity;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;

/**
 * Created by Elad on 5/31/2018.
 */

public class Repository {

    final private static String TAG = Repository.class.getSimpleName();
    private RedditNetwork network;
    final private SavedPostsStorge savedPostsStorge;
    private static Repository instance;
    public static Repository getInstance(Context context, String channelName) throws IOException {
        if(instance == null){
            instance = new Repository(context, channelName);
        }
        return instance;
    }

    private Repository(Context context, String channelName) throws IOException {
        network = new RedditNetwork(channelName);
        savedPostsStorge = new SavedPostsStorge(context);
    }

    public LiveData<PagedList<RedditPost>> getRedditList() {

        return network.getRedditList();
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }

    public void setNewChannel(String channelName) {
        network = new RedditNetwork(channelName);
    }

    public void addItemToStorage(RedditPost redditPost, Context context) throws IOException, JSONException {
        savedPostsStorge.addItem(redditPost, context);
    }

    public List<RedditPost> getSavedPosts() throws IOException {
        return savedPostsStorge.getSavedPosts();
    }

    public List<RedditPost> startQuery(String query) throws IOException {
        return savedPostsStorge.search("*" + query + "*");
    }
}
