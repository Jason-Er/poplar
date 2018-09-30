package com.wecyberstage.wecyberstage.view.message;

import android.graphics.Rect;

import com.wecyberstage.wecyberstage.model.StageLine;

public class MaskClickEvent {

    private String message;
    private StageLine stageLine;
    private Rect rect;

    public MaskClickEvent(String message, StageLine stageLine, Rect rect) {
        this.message = message;
        this.stageLine = stageLine;
        this.rect = rect;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StageLine getStageLine() {
        return stageLine;
    }

    public void setStageLine(StageLine stageLine) {
        this.stageLine = stageLine;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
