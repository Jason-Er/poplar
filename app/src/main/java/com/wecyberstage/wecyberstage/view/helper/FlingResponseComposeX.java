package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeY.ComposeY;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseComposeX implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseComposeX(CustomViewSlideControl control) {
        this.control = control;
    }
    @Override
    public void toLeft() {
        Log.i("flingComposeX", "toLeft");
        ComposeX composeX = (ComposeX) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        ComposeY composeY = (ComposeY) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal());
        composeY.setPlayState(composeX.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE_Y, CustomViewSlideControl.Direction.TO_LEFT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Y.ordinal())).view;
    }

    @Override
    public void toRight() {
        Log.i("flingComposeX", "toRight");
        ComposeX composeX = (ComposeX) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        ComposeZ composeZ = (ComposeZ) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal());
        composeZ.setPlayState(composeX.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE_Z, CustomViewSlideControl.Direction.TO_RIGHT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal())).view;
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
