package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class FlingResponseComposeX implements FlingResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public FlingResponseComposeX(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i("flingComposeX", "toLeft");
        slideViewInterface.slideTo(ViewType.COMPOSE_X, ViewType.COMPOSE_Y, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeX", "toRight");
        slideViewInterface.slideTo(ViewType.COMPOSE_X, ViewType.COMPOSE_Z, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
