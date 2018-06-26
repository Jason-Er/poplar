package com.wecyberstage.wecyberstage.view.main;

import android.view.GestureDetector;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    @Provides
    @Named("composeZ")
    public GestureDetector providePresenter(MainActivity activity){
        return null;
    }
}
