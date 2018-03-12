package com.wecyberstage.wecyberstage.view.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mike on 2018/3/10.
 */
@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}
