package com.wecyberstage.wecyberstage.message;

import com.wecyberstage.wecyberstage.model.StageLine;

public class MaskChooseEvent {
    private String message;
    private StageLine stageLine;

    public MaskChooseEvent(String message, StageLine stageLine) {
        this.message = message;
        this.stageLine = stageLine;
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

}
