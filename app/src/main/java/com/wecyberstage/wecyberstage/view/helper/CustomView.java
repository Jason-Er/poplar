package com.wecyberstage.wecyberstage.view.helper;

import android.view.View;

public class CustomView {
    public View view;
    public ViewOnTouch viewOnTouch;

    public CustomView() {

    }

    public CustomView(View view, ViewOnTouch viewOnTouch) {
        this.view = view;
        this.viewOnTouch = viewOnTouch;
    }
}
