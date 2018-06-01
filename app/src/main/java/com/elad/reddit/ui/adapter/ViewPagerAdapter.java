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
    private Fragment current;
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mListFragmentTitles = new ArrayList<>();
    }

    public Fragment getCurrent() {
        return current;
    }

    @Override
    public Fragment getItem(int position) {
        current = mFragmentList.get(position);
        return current;
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

    public void updatePageTitle(String title) {
        if (mFragmentList.indexOf(current) >= 0) {
            mListFragmentTitles.set(mFragmentList.indexOf(current), title);
            notifyDataSetChanged();
        }
    }
}
