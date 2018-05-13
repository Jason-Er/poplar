package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseComposeZ implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseComposeZ(CustomViewSlideControl control) {
        this.control = control;
    }

    @Override
    public void toLeft() {
        Log.i("flingComposeX", "toLeft");
        ComposeZ composeZ = (ComposeZ) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal());
        ComposeX composeX = (ComposeX) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        composeX.setPlayState(composeZ.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE_X, CustomViewSlideControl.Direction.TO_LEFT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal())).view;
    }

    @Override
    public void toRight() {
        Log.i("flingComposeY", "toRight");
        ComposeZ composeZ = (ComposeZ) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal());
        ComposeY composeY = (ComposeY) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal());
        composeY.setPlayState(composeZ.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE_Y, CustomViewSlideControl.Direction.TO_RIGHT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal())).view;
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
