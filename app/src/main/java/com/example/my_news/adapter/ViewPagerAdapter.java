package com.example.my_news.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.my_news.fragments.MostPopularFragment;
import com.example.my_news.fragments.TopStoriesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Top Stories";
            case 1:
                return "Most Popular";
            case 2:
                return "Technology";
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new TopStoriesFragment();
        } else if (i == 1) {
            return new MostPopularFragment();
        } else {
            //change to show search results for tech stories
            return new TopStoriesFragment();
        }
    }
}
