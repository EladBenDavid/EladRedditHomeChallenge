package com.elad.reddit.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.ui.adapter.RedditPageListAdapter;
import com.elad.reddit.ui.listeners.OnTopbarTitleChange;

import org.parceler.Parcels;

/**
 * Created by Elad on 5/28/2018.
 */

public class FeedFragment extends BaseFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,container,savedInstanceState);
        observersRegisters();
        return view;
    }

    private void observersRegisters(){
        final RedditPageListAdapter redditPageListAdapter = new RedditPageListAdapter(this);
        viewModel.getRedditList().observe(this, redditPageListAdapter::submitList);
        viewModel.getNetworkState().observe(this, networkState -> {
            redditPageListAdapter.setNetworkState(networkState);
        });
        recyclerView.setAdapter(redditPageListAdapter);
    }

    @Override
    public void OnItemClick(View view, int position) {
        Intent startWeb = new Intent(getActivity(), WebActivity.class);
        RedditPost clickedPost = viewModel.getRedditList().getValue().get(position);
        startWeb.putExtra(WebActivity.REEDIT_POST_KEY, Parcels.wrap(clickedPost));
        startActivity(startWeb);
    }

    @Override
    public void onSearchRequest(String query) {
        viewModel.setNewChannel(query);
        observersRegisters();
        if(getActivity() instanceof OnTopbarTitleChange){
            ((OnTopbarTitleChange)getActivity()).OnTopbarTitleChange(query);
        }
    }
}
