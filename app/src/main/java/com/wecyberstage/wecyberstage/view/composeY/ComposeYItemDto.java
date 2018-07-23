package com.wecyberstage.wecyberstage.view.composeY;

import com.wecyberstage.wecyberstage.model.StageLine;

public class ComposeYItemDto {
    private ComposeYCardViewType viewType;
    private StageLine stageLine;

    public ComposeYItemDto(ComposeYCardViewType viewType, StageLine stageLine) {
        this.viewType = viewType;
        this.stageLine = stageLine;
    }

    public ComposeYCardViewType getViewType() {
        return viewType;
    }

    public StageLine getStageLine() {
        return stageLine;
    }
}
