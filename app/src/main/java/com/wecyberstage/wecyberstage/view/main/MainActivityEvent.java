package com.wecyberstage.wecyberstage.view.main;

public class MainActivityEvent {
    private Object data;
    private String message;

    public MainActivityEvent(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
