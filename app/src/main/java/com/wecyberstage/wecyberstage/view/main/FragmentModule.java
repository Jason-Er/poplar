package com.wecyberstage.wecyberstage.view.main;

import android.view.GestureDetector;

import com.wecyberstage.wecyberstage.view.participate.ParticipateGestureDetectorListener;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    @Provides
    @Named("participate")
    public GestureDetector providePresenter(MainActivity activity, ParticipateGestureDetectorListener participateGestureDetectorListener){
        return new GestureDetector(activity, participateGestureDetectorListener);
    }
}
