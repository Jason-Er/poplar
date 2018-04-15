package com.wecyberstage.wecyberstage.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private FragmentManager fragmentManager;
    private List<String> tags;

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
        list = new ArrayList<>();
        tags = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = list.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void setDataSet(List<Fragment> list) {
        if (!this.tags.isEmpty()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.list = list;
        notifyDataSetChanged();
    }

}
