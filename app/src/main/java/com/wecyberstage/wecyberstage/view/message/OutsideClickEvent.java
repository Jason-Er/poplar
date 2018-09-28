package com.wecyberstage.wecyberstage.view.message;

public class OutsideClickEvent {
    private String message;

    public OutsideClickEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
