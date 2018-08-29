package com.wecyberstage.wecyberstage.view.main;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaskChooseTabLayout extends LinearLayout {

    @BindView(R.id.maskChooseTabLayout_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.maskChooseTabLayout_viewPager)
    ViewPager viewPager;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    public MaskChooseTabLayout(Context context) {
        super(context);
    }

    public MaskChooseTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskChooseTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        initViewPager();
        initTabLayout();
    }

    private void initTabLayout(){
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabIndicators.size(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab!=null){
                itemTab.setCustomView(R.layout.item_tab_layout_role);
                TextView itemTv = itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(tabIndicators.get(i));
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    private void initViewPager(){
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabIndicators.add("Tab " + i);
        }
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(((AppCompatActivity)getContext()).getSupportFragmentManager());
        viewPager.setAdapter(contentAdapter);
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

    }
}
