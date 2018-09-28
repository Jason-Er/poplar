package com.wecyberstage.wecyberstage.view.message;

public class MessageEvent {

    private Object data;
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(Object data) {
        this.data = data;
    }

    public MessageEvent(Object data, String message) {
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
