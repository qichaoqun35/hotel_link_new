package com.example.qichaoqun.amerilink.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 功能：主页引导栏的三个Fragment页面设置适配器
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int fragment) {
        return fragments.get(fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
