package com.wecyberstage.wecyberstage.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list = new ArrayList<>();

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setDataSet(List<Fragment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
