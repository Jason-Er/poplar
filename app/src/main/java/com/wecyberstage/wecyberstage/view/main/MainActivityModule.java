package com.wecyberstage.wecyberstage.view.main;

import com.wecyberstage.wecyberstage.util.label.PerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mike on 2018/3/10.
 */
@Module
public abstract class MainActivityModule {
    @PerActivity
    @ContributesAndroidInjector(modules = {FragmentBuildersModule.class, FragmentModule.class})
    abstract MainActivity contributeMainActivity();
}
