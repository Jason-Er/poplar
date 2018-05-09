package com.wecyberstage.wecyberstage.view.helper;

import android.util.Log;

public class FlingResponseParticipate implements FlingResponseInterface {

    CustomViewSlideControl control;
    public FlingResponseParticipate(CustomViewSlideControl control) {
        this.control = control;
    }

    @Override
    public void toLeft() {

    }

    @Override
    public void toRight() {
        Log.i("flingParticipate", "toRight");
        control.navigateToView(CustomViewSlideControl.ViewType.COMPOSE);
        control.currentFlingResponse = (FlingResponseInterface) control.flingResponseArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal());
        control.currentView = ((CustomView) control.viewArray.get(CustomViewSlideControl.ViewType.COMPOSE.ordinal())).view;
    }

    @Override
    public void toUp() {

    }

    @Override
    public void toDown() {

    }
}
