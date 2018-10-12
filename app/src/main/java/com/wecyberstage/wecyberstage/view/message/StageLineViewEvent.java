package com.wecyberstage.wecyberstage.view.message;

public class StageLineViewEvent extends BaseEvent {

    public StageLineViewEvent(Object data, String message) {
        super(data, message);
    }

    public StageLineViewEvent(String message) {
        super(message);
    }
}
