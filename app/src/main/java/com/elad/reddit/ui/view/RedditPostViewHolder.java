package com.elad.reddit.ui.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.elad.reddit.R;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.ui.listeners.ItemClickListener;


public class RedditPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView titleTextView;
    private TextView subtitleTextView;
    private TextView subtitle2TextView;
    private ImageView thumbnailImageView;
    private ItemClickListener itemClickListener;
    public RedditPostViewHolder(View view, ItemClickListener itemClickListener) {
        super(view);
        this.titleTextView = (TextView) view.findViewById(R.id.title);
        this.subtitleTextView = (TextView) view.findViewById(R.id.subtitle);
        this.subtitle2TextView = (TextView) view.findViewById(R.id.subtitle2);
        this.thumbnailImageView = (ImageView)view.findViewById(R.id.thumbnail);
        this.itemClickListener = itemClickListener;
        view.setOnClickListener(this);

    }

    public void bindTo(RedditPost redditPost) {
        titleTextView.setText(redditPost.getTitle());
        subtitleTextView.setText(String.format("%s - %s - %s (%s)", redditPost.getAuthor(), redditPost.getCreatedOn(), redditPost.getSubreddit(), redditPost.getDomain()));
        subtitle2TextView.setText(String.format("%d points - %d comments", redditPost.getScore(), redditPost.getNumComments()));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(itemView.getContext())
                .load(redditPost.getThumbnail())
                .apply(requestOptions)
                .into(thumbnailImageView);
    }
    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.OnItemClick(view, getAdapterPosition()); // call the onClick in the OnItemClickListener
        }
    }
}
