package com.wecyberstage.wecyberstage.message;

import android.graphics.Rect;

public class MaskClickEvent {

    private String message;
    private long id;
    private Rect rect;

    public MaskClickEvent(String message, long id, Rect rect) {
        this.message = message;
        this.id = id;
        this.rect = rect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
