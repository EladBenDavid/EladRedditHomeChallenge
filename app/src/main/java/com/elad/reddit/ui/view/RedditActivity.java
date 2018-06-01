package com.elad.reddit.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.SearchView;

import com.elad.reddit.R;
import com.elad.reddit.ui.adapter.ViewPagerAdapter;
import com.elad.reddit.ui.listeners.OnSearchQuery;
import com.elad.reddit.ui.listeners.OnTopbarTitleChange;

/**
 * Created by Elad on 5/30/2018.
 */
public class RedditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, SearchView.OnQueryTextListener , OnTopbarTitleChange {

    public static final String SAVED_POST_REPO_NAME = "saved_post_repo_name";
    public static final String DEFAULT_CHANNEL = "popular";
    private static final String TAG = RedditActivity.class.getSimpleName();
    private static final int CHANNEL_POSTS_PAGE_INDEX = 0;
    private static final String CHANNEL_ID = "channel_id";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_reddit);
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FeedFragment(), DEFAULT_CHANNEL);
        viewPagerAdapter.addFragment(new SavedPostsFragment(), getString(R.string.saved_posts));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
        // we change the search hint according the page we present
        if(position == CHANNEL_POSTS_PAGE_INDEX){
            searchView.setQueryHint(getString(R.string.channel_search));
        }else{
            searchView.setQueryHint(getString(R.string.saved_posts_search));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageScrollStateChanged(int state) {}


    @Override
    public boolean onQueryTextSubmit(String text) {
        Fragment current = viewPagerAdapter.getCurrent();
        if(current instanceof OnSearchQuery){
            ((OnSearchQuery)current).onSearchRequest(text);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) { return false; }

    @Override
    public void OnTopbarTitleChange(String title) {
        viewPagerAdapter.updatePageTitle(title);
    }
}