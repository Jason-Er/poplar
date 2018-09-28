package com.wecyberstage.wecyberstage.view.message;

public class StageLineCardViewEvent extends BaseEvent {

    public StageLineCardViewEvent(Object data, String message) {
        super(data, message);
    }

    public StageLineCardViewEvent(String message) {
        super(message);
    }
}
