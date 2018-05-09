package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.compose.Compose;
import com.wecyberstage.wecyberstage.view.participate.Participate;

public class FlingResponseParticipate implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseParticipate(CustomViewSlideControl control) {
        this.control = control;
    }

    @Override
    public void toLeft() {
        Log.i("flingParticipate", "toLeft");
        Participate participate = (Participate) control.viewArray.get(CustomViewSlideControl.ViewType.PARTICIPANT.ordinal());
        Compose compose = (Compose) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal());
        compose.setPlayState(participate.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE, CustomViewSlideControl.Direction.TO_LEFT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal())).view;
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
