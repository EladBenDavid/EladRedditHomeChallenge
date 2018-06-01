package com.elad.reddit.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elad.reddit.R;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.ui.adapter.SavedPostsAdapter;
import com.elad.reddit.ui.viewmodel.FeedViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class SavedPostsFragment extends BaseFragment{

    private SavedPostsAdapter savedPostsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        try {
            List<RedditPost> savedPosts =  viewModel.getSavedPosts();
            savedPostsAdapter = new SavedPostsAdapter(savedPosts);
            recyclerView.setAdapter(savedPostsAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void OnItemClick(View view, int position) { }
    @Override
    public void onSearchRequest(String query) {
        try {
            List<RedditPost> savedPosts =  viewModel.startQuery(query);
            savedPostsAdapter.setNewDataSource(savedPosts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
