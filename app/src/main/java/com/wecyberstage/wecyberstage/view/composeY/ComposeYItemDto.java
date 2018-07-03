package com.wecyberstage.wecyberstage.view.composeY;

import com.wecyberstage.wecyberstage.model.ComposeLine;

public class ComposeYItemDto {
    private ComposeYCardViewType viewType;
    private ComposeLine composeLine;

    public ComposeYItemDto(ComposeYCardViewType viewType, ComposeLine composeLine) {
        this.viewType = viewType;
        this.composeLine = composeLine;
    }

    public ComposeYCardViewType getViewType() {
        return viewType;
    }

    public ComposeLine getComposeLine() {
        return composeLine;
    }
}
