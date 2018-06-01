package com.elad.reddit.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elad.reddit.R;
import com.elad.reddit.ui.listeners.ItemClickListener;
import com.elad.reddit.ui.listeners.OnSearchQuery;
import com.elad.reddit.ui.viewmodel.FeedViewModel;

import java.io.IOException;

public abstract class BaseFragment extends Fragment implements ItemClickListener, OnSearchQuery {

    protected FeedViewModel viewModel;
    protected RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewModel = ViewModelProviders.of(getActivity()).get(FeedViewModel.class);
        try {
            viewModel.initRepository(this.getActivity().getApplicationContext(), RedditActivity.DEFAULT_CHANNEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }
}
