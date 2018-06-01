package com.elad.reddit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elad.reddit.R;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.ui.view.RedditPostViewHolder;

import java.util.List;
import java.util.Set;

public class SavedPostsAdapter extends RecyclerView.Adapter<RedditPostViewHolder> {
    private List<RedditPost> savedPosts;



    // Provide a suitable constructor (depends on the kind of dataset)
    public SavedPostsAdapter(List<RedditPost> savedPosts) {
        this.savedPosts = savedPosts;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RedditPostViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // create a new view
        View view = layoutInflater.inflate(R.layout.reddit_post_item, parent, false);
        RedditPostViewHolder viewHolder = new RedditPostViewHolder(view, null);;
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RedditPostViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((RedditPostViewHolder) holder).bindTo(savedPosts.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return savedPosts.size();
    }

    public void setNewDataSource(List<RedditPost> savedPosts) {
        this.savedPosts = savedPosts;
        notifyDataSetChanged();
    }
}