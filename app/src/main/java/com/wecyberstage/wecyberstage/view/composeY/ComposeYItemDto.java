package com.wecyberstage.wecyberstage.view.composeY;

import com.wecyberstage.wecyberstage.model.ComposeScript;

public class ComposeYItemDto {
    private ComposeYCardViewType viewType;
    private ComposeScript.AvatarLine avatarLine;

    public ComposeYItemDto(ComposeYCardViewType viewType, ComposeScript.AvatarLine avatarLine) {
        this.viewType = viewType;
        this.avatarLine = avatarLine;
    }

    public ComposeYCardViewType getViewType() {
        return viewType;
    }

    public ComposeScript.AvatarLine getAvatarLine() {
        return avatarLine;
    }
}
