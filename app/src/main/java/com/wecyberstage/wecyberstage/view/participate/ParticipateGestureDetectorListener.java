package com.wecyberstage.wecyberstage.view.participate;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wecyberstage.wecyberstage.util.label.PerActivity;
import com.wecyberstage.wecyberstage.view.main.NavigationController;

import javax.inject.Inject;

@PerActivity
public class ParticipateGestureDetectorListener extends GestureDetector.SimpleOnGestureListener  {

    private NavigationController navigationController;
    @Inject
    public ParticipateGestureDetectorListener(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        navigationController.navigateToBrowse();
        return true;
    }
}
