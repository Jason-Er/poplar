package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class BehaviorResponseBrowsePrivate implements BehaviorResponseInterface {

    private final String TAG = "ResponseBrowsePrivate";
    CustomViewSlideInterface slideViewInterface;
    public BehaviorResponseBrowsePrivate(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i(TAG, "toLeft()");
        slideViewInterface.slideTo(ViewType.BROWSE_PRIVATE, ViewType.BROWSE_PUBLIC, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i(TAG, "toRight()");
        slideViewInterface.slideTo(ViewType.BROWSE_PRIVATE, ViewType.BROWSE_PUBLIC, Direction.TO_RIGHT);
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
