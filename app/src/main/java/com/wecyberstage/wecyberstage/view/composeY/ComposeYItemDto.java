package com.wecyberstage.wecyberstage.view.composeY;

import com.wecyberstage.wecyberstage.model.ComposeScript;

public class ComposeYItemDto {
    private ComposeYCardViewType viewType;
    private ComposeScript.Avatar_Line avatarLine;

    public ComposeYItemDto(ComposeYCardViewType viewType, ComposeScript.Avatar_Line avatarLine) {
        this.viewType = viewType;
        this.avatarLine = avatarLine;
    }

    public ComposeYCardViewType getViewType() {
        return viewType;
    }

    public ComposeScript.Avatar_Line getAvatarLine() {
        return avatarLine;
    }
}
