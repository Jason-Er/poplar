package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StagePlayEntity;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class StagePlay extends StagePlayEntity {
    public User director;
    public Stage stage;
    public List<StageScene> scenes;
    public List<StageRole> cast; // all roles in the play
}
