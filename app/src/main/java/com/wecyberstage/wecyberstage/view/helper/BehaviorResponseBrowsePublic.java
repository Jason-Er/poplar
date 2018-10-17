package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class BehaviorResponseBrowsePublic implements BehaviorResponseInterface {

    private final String TAG = "ResponseBrowsePublic";
    CustomViewSlideInterface slideViewInterface;
    public BehaviorResponseBrowsePublic(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }

    @Override
    public void toLeft() {
        Log.i(TAG, "toLeft()");
        slideViewInterface.slideTo(ViewType.BROWSE_PUBLIC, ViewType.BROWSE_PRIVATE, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i(TAG, "toRight()");
        slideViewInterface.slideTo(ViewType.BROWSE_PUBLIC, ViewType.BROWSE_PRIVATE, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }

    @Override
    public void click() {

    }
}
