package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.LineEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class Line extends LineEntity {
    public long duration;
    public Line() {}
    public Line(long roleId, String dialogue, long beginTime, long duration) {
        this.roleId = roleId;
        this.dialogue = dialogue;
        this.beginTime = beginTime;
        this.duration = duration;
    }
}
