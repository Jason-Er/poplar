package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StageLineEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class StageLine extends StageLineEntity implements Cloneable {
    public Voice voice;
    public StageRole stageRole;
    public StageLine() {}
    // TODO: 7/23/2018 below constructor used for test temporarily
    public StageLine(StageRole stageRole, String dialogue, long beginTime, long duration, int maskOrdinal) {
        this.stageRole = stageRole;
        this.dialogue = dialogue;
        this.beginTime = beginTime;
        this.voice = new Voice();
        this.voice.duration = duration;
        this.maskOrdinal = maskOrdinal;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getRoleName() {
        return stageRole.name;
    }

    public void setStageRole(StageRole stageRole) {
        this.stageRole = stageRole;
    }

    public String getDialogue() {
        return dialogue;
    }

    public MaskGraph getMaskGraph() {
        return stageRole.getMaskGraph(maskOrdinal);
    }
}
