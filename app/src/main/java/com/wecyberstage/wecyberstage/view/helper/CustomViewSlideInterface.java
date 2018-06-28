package com.wecyberstage.wecyberstage.view.helper;

public interface CustomViewSlideInterface {
    CustomView getCustomView(ViewType viewType);
    // void slideTo(final CustomView from, final CustomView to, Direction direction);
    void slideUp();
    void slideTo(ViewType to, Direction direction);
    void slideTo(ViewType to, Direction direction, boolean saveTrack);
    void slideTo(ViewType from, ViewType to, Direction direction);
    void slideTo(ViewType from, ViewType to, Direction direction, boolean saveTrack);
}
