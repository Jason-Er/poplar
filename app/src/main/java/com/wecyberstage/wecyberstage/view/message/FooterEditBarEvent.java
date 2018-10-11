package com.wecyberstage.wecyberstage.view.message;

public class FooterEditBarEvent extends BaseEvent {

    public FooterEditBarEvent(String message) {
        super(message);
    }

    public FooterEditBarEvent(Object data) {
        super(data);
    }

    public FooterEditBarEvent(Object data, String message) {
        super(data, message);
    }
}
