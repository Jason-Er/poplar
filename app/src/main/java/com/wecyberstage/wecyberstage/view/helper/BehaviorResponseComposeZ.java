package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class BehaviorResponseComposeZ implements BehaviorResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public BehaviorResponseComposeZ(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }

    @Override
    public void toLeft() {
        Log.i("flingComposeZ", "toLeft");
        slideViewInterface.slideTo(ViewType.COMPOSE_Z, ViewType.COMPOSE_X, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeZ", "toRight");
        slideViewInterface.slideTo(ViewType.COMPOSE_Z, ViewType.COMPOSE_Y, Direction.TO_RIGHT);
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
