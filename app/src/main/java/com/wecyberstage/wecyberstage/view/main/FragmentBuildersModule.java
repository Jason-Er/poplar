package com.wecyberstage.wecyberstage.view.main;

import com.wecyberstage.wecyberstage.view.browse.Browse;
import com.wecyberstage.wecyberstage.view.participate.Participate;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mike on 2018/3/12.
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract Browse contributeBrowseFragment();
    @ContributesAndroidInjector
    abstract Participate contributeParticipateFragment();
}
