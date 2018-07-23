package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StageSceneEntity;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class StageScene extends StageSceneEntity {
    public ActionScript actionScript;
    public List<StageLine> stageLines;
    public List<StageRole> stageRoles; // for roles in the scene
    public List<Prop> props; // for props used in the scene
}
