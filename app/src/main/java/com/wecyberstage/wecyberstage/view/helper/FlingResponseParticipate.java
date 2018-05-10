package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseParticipate implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseParticipate(CustomViewSlideControl control) {
        this.control = control;
    }

    @Override
    public void toLeft() {
        Log.i("flingParticipate", "toLeft");
        ComposeZ participate = (ComposeZ) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_Z.ordinal());
        ComposeX compose = (ComposeX) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        compose.setPlayState(participate.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE_X, CustomViewSlideControl.Direction.TO_LEFT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE_X.ordinal())).view;
    }

    @Override
    public void toRight() {
        Log.i("flingParticipate", "toRight");



    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
