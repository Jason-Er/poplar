package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseComposeX implements FlingResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public FlingResponseComposeX(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i("flingComposeX", "toLeft");
        slideViewInterface.slideView(ViewType.COMPOSE_X, ViewType.COMPOSE_Y, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeX", "toRight");
        slideViewInterface.slideView(ViewType.COMPOSE_X, ViewType.COMPOSE_Z, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
