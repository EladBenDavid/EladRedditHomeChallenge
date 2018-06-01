package com.elad.reddit.ui.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.elad.reddit.R;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.service.model.NetworkState;
import com.elad.reddit.ui.listeners.ItemClickListener;
import com.elad.reddit.ui.view.NetworkStateItemViewHolder;
import com.elad.reddit.ui.view.RedditPostViewHolder;

/**
 * Created by Elad on 5/28/2018.
 */

public class RedditPageListAdapter extends PagedListAdapter<RedditPost, RecyclerView.ViewHolder> {

    private static final String TAG = "UserAdapter";
    private NetworkState networkState;
    private ItemClickListener itemClickListener;

    public RedditPageListAdapter(ItemClickListener itemClickListener) {
        super(RedditPost.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == R.layout.reddit_post_item) {
            view = layoutInflater.inflate(R.layout.reddit_post_item, parent, false);
            RedditPostViewHolder viewHolder = new RedditPostViewHolder(view, itemClickListener);;
            return viewHolder;
        } else if (viewType == R.layout.network_state_item) {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.reddit_post_item:
                ((RedditPostViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.reddit_post_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }
}
