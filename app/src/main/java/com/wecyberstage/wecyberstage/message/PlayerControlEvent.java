package com.wecyberstage.wecyberstage.message;

public class PlayerControlEvent {
    private String message;

    public PlayerControlEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
