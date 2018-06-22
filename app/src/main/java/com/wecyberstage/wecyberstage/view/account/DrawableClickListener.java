package com.wecyberstage.wecyberstage.view.account;

public interface DrawableClickListener {
    static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    void onClick(DrawablePosition target);
}
