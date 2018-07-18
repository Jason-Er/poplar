package com.wecyberstage.wecyberstage.message;

import android.graphics.Point;

public class MaskClickEvent {

    private String message;
    private long id;
    private Point point;

    public MaskClickEvent(String message, long id, Point point) {
        this.message = message;
        this.id = id;
        this.point = point;
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

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
