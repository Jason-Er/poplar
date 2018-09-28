package com.wecyberstage.wecyberstage.view.message;

public class FooterEditMainEvent {
    private Object stageLine; // one line or lines

    public FooterEditMainEvent(Object stageLine) {
        this.stageLine = stageLine;
    }

    public Object getStageLine() {
        return stageLine;
    }

    public void setStageLine(Object stageLine) {
        this.stageLine = stageLine;
    }
}
