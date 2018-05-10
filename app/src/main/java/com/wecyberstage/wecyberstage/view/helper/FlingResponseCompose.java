package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

import com.wecyberstage.wecyberstage.view.composeX.ComposeX;
import com.wecyberstage.wecyberstage.view.composeZ.ComposeZ;

public class FlingResponseCompose implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseCompose(CustomViewSlideControl control) {
        this.control = control;
    }
    @Override
    public void toLeft() {

    }

    @Override
    public void toRight() {
        Log.i("flingCompose", "toRight");
        ComposeZ participate = (ComposeZ) control.viewArray.get(CustomViewSlideControl.ViewType.PARTICIPANT.ordinal());
        ComposeX compose = (ComposeX) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal());
        participate.setPlayState(compose.getPlayState());
        control.navigateToView(CustomViewSlideControl.ViewType.PARTICIPANT, CustomViewSlideControl.Direction.TO_RIGHT);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.PARTICIPANT.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.PARTICIPANT.ordinal())).view;
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
