package com.wecyberstage.wecyberstage.view.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.StageRole;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaskChooseTabLayout extends LinearLayout {

    @BindView(R.id.maskChooseTabLayout_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.maskChooseTabLayout_viewPager)
    ViewPager viewPager;

    private List<StageRole> stageRoles;

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

        MaskPagerAdapter maskPagerAdapter = new MaskPagerAdapter();
        viewPager.setAdapter(maskPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tabLayout, 10);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab!=null){
                itemTab.setCustomView(R.layout.item_tab_layout_role);
                TextView itemTv = itemTab.getCustomView().findViewById(R.id.tv_menu_item);
                itemTv.setText(i+"");
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    /*
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
    */

    public void setStageRoles(List<StageRole> stageRoles) {
        this.stageRoles = stageRoles;
        // contentAdapter.notifyDataSetChanged();

    }

    class MaskPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.ic_account);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
