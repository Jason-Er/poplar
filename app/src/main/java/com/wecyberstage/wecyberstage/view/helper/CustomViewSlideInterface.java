package com.wecyberstage.wecyberstage.view.helper;

public interface CustomViewSlideInterface {
    BaseView getCustomView(ViewType viewType);
    // void slideTo(final BaseView from, final BaseView to, Direction direction);
    void slideUp();
    void slideTo(ViewType to, Direction direction);
    void slideTo(ViewType to, Direction direction, boolean saveTrack);
    void slideTo(ViewType from, ViewType to, Direction direction);
    void slideTo(ViewType from, ViewType to, Direction direction, boolean saveTrack);
}
