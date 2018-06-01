package com.elad.reddit.ui.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.elad.reddit.R;
import com.elad.reddit.service.repository.storge.SavedPostsStorge;
import com.elad.reddit.service.repository.network.api.RedditApi;
import com.elad.reddit.service.model.RedditPost;
import com.elad.reddit.ui.viewmodel.FeedViewModel;

import org.json.JSONException;
import org.parceler.Parcels;

import java.io.IOException;

/**
 * Created by Elad on 5/30/2018.
 */

public class WebActivity extends AppCompatActivity  {

    public static final String REEDIT_POST_KEY = "reedit_post_key";
    private RedditPost redditPost;
    private FeedViewModel viewModel;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brawser_activity);
        viewModel = ViewModelProviders.of(this).get(FeedViewModel.class);
        try {
            viewModel.initRepository(getApplicationContext(), RedditActivity.DEFAULT_CHANNEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(getIntent().getExtras() != null && getIntent().getParcelableExtra(REEDIT_POST_KEY) != null){
            redditPost = Parcels.unwrap(getIntent().getParcelableExtra(REEDIT_POST_KEY));
            WebView webView = findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(RedditApi.BASE_URL + redditPost.getPermalink());
         }
    }

    public void savePost(View view) {
        try {
            viewModel.addItemToStorage(redditPost, this);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
