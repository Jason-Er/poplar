package com.wecyberstage.wecyberstage.view.helper;

public interface CustomViewSlideInterface {
    CustomView getCustomView(ViewType viewType);
    void slideView(final CustomView from, final CustomView to, Direction direction);
    void slideView(ViewType from, ViewType to, Direction direction);
}
