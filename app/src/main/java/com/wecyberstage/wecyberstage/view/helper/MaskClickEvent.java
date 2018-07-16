package com.wecyberstage.wecyberstage.view.helper;

public class MaskClickEvent {

    private String message;
    private long id;

    public MaskClickEvent(String message, long id) {
        this.message = message;
        this.id = id;
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
}
