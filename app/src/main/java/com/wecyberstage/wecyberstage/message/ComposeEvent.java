package com.wecyberstage.wecyberstage.message;

public class ComposeEvent {
    private String message;
    float seekProcess;
    public ComposeEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public float getSeekProcess() {
        return seekProcess;
    }

    public void setSeekProcess(float seekProcess) {
        this.seekProcess = seekProcess;
    }
}
