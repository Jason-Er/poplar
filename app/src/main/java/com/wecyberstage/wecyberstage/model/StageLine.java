package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StageLineEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class StageLine extends StageLineEntity {
    public MaskGraph maskGraph;
    public Voice voice;
    public StageLine() {}
    // TODO: 7/23/2018 below constructor used for test temporarily
    public StageLine(long roleId, String dialogue, long beginTime, long duration, MaskGraph maskGraph) {
        this.roleId = roleId;
        this.dialogue = dialogue;
        this.beginTime = beginTime;
        this.voice.duration = duration;
        this.maskGraph = maskGraph;
    }
}
