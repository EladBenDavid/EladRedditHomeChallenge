package com.elad.reddit.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elad on 5/28/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList;
    private final List<String> mListFragmentTitles;
     public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mListFragmentTitles = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mListFragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListFragmentTitles.get(position);
    }

    public void updatePageTitle(int itemPosition, String title) {
        if (mFragmentList.indexOf(itemPosition) >= 0) {
            mListFragmentTitles.set(mFragmentList.indexOf(itemPosition), title);
            notifyDataSetChanged();
        }
    }

    public Fragment getFragment(int itemIndex) {
        return mFragmentList.get(itemIndex);
    }
}
