package com.wecyberstage.wecyberstage.message;

import com.wecyberstage.wecyberstage.model.MaskGraph;
import com.wecyberstage.wecyberstage.model.StageLine;

public class MaskChooseEvent {
    private String message;
    private StageLine stageLine;
    private MaskGraph maskGraph;

    public MaskChooseEvent(String message, StageLine stageLine, MaskGraph maskGraph) {
        this.message = message;
        this.stageLine = stageLine;
        this.maskGraph = maskGraph;
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

    public MaskGraph getMaskGraph() {
        return maskGraph;
    }

    public void setMaskGraph(MaskGraph maskGraph) {
        this.maskGraph = maskGraph;
    }
}
