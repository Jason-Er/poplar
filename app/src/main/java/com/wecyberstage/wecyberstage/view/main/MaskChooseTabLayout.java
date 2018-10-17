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

import com.bumptech.glide.Glide;
import com.wecyberstage.wecyberstage.R;
import com.wecyberstage.wecyberstage.model.StageRole;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaskChooseTabLayout extends LinearLayout {

    @BindView(R.id.maskChooseTabLayout_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.maskChooseTabLayout_viewPager)
    ViewPager viewPager;

    private List<StageRole> cast;
    private MaskPagerAdapter maskPagerAdapter;
    private List<MaskGridLayout> maskGridLayoutList;
    private MaskGridLayoutCallBack maskGridLayoutCallBack;

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

        maskPagerAdapter = new MaskPagerAdapter();
        viewPager.setAdapter(maskPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(tabLayout, 10);
    }

    private void updateTabLayout() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab itemTab = tabLayout.getTabAt(i);
            if (itemTab!=null){
                ImageView imageView = new ImageView(getContext());
                Glide.with(getContext()).load(cast.get(i).mask.maskGraphList.get(0).graphURL).into(imageView);
                itemTab.setCustomView(imageView);
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    public void setMaskGridLayoutCallBack(MaskGridLayoutCallBack maskGridLayoutCallBack) {
        this.maskGridLayoutCallBack = maskGridLayoutCallBack;
    }

    public void setCast(List<StageRole> cast) {
        this.cast = cast;
        maskPagerAdapter.notifyDataSetChanged();
        updateTabLayout();
    }

    class MaskPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return cast ==null? 0 : cast.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if( maskGridLayoutList == null ) maskGridLayoutList = new ArrayList<>();
            if( maskGridLayoutList.size() <= position ) {
                maskGridLayoutList.add(new MaskGridLayout(getContext(), maskGridLayoutCallBack));
            }
            MaskGridLayout maskGridLayout = maskGridLayoutList.get(position);
            maskGridLayout.setStageRole(cast.get(position));
            container.addView(maskGridLayout);
            return maskGridLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
