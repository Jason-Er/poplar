package com.wecyberstage.wecyberstage.model;

import java.util.ArrayList;
import java.util.List;

public class ComposeScript {

    public long playId;
    public long sceneId;
    public List<ComposeLine> composeLineList;
    public List<Role> roleList;

    public ComposeScript(long playId, long sceneId) {
        this.playId = playId;
        this.sceneId = sceneId;
        composeLineList = new ArrayList<>();
        roleList = new ArrayList<>();
    }
}
