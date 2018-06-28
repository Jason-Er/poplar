package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class FlingResponseComposeY implements FlingResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public FlingResponseComposeY(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i("flingComposeY", "toLeft");
        slideViewInterface.slideTo(ViewType.COMPOSE_Y, ViewType.COMPOSE_Z, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeY", "toRight");
        slideViewInterface.slideTo(ViewType.COMPOSE_Y, ViewType.COMPOSE_X, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
