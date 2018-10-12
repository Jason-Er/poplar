package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class BehaviorResponseComposeY implements BehaviorResponseInterface {

    private final String TAG = "BehaviorComposeY";
    CustomViewSlideInterface slideViewInterface;
    public BehaviorResponseComposeY(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i(TAG, "toLeft");
        slideViewInterface.slideTo(ViewType.COMPOSE_Y, ViewType.COMPOSE_Z, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i(TAG, "toRight");
        slideViewInterface.slideTo(ViewType.COMPOSE_Y, ViewType.COMPOSE_X, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }

    @Override
    public void click() {
        Log.d(TAG, "click");

    }
}
