package com.wecyberstage.wecyberstage.view.composeX;

public class ComposeXDto implements ViewTypeInterface{
    String text;
    float startTime;
    float duration;

    public ComposeXDto(String text, float startTime, float duration) {
        this.text = text;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getStartTime() {
        return startTime;
    }

    public float getDuration() {
        return duration;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }
}
