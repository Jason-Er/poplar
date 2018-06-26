package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseComposeY implements FlingResponseInterface {

    CustomViewSlideInterface slideViewInterface;
    public FlingResponseComposeY(CustomViewSlideInterface slideViewInterface) {
        this.slideViewInterface = slideViewInterface;
    }
    @Override
    public void toLeft() {
        Log.i("flingComposeY", "toLeft");
        slideViewInterface.slideView(ViewType.COMPOSE_Y, ViewType.COMPOSE_Z, Direction.TO_LEFT);
    }

    @Override
    public void toRight() {
        Log.i("flingComposeY", "toRight");
        slideViewInterface.slideView(ViewType.COMPOSE_Y, ViewType.COMPOSE_X, Direction.TO_RIGHT);
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
