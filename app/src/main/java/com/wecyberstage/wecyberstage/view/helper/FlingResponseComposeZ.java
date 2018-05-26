package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseComposeZ implements FlingResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public FlingResponseComposeZ(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }

    @Override
    public void toLeft() {
        Log.i("flingComposeZ", "toLeft");
        slideViewInterface.slideView(ViewType.COMPOSE_Z, ViewType.COMPOSE_X, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeZ", "toRight");
        slideViewInterface.slideView(ViewType.COMPOSE_Z, ViewType.COMPOSE_Y, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
