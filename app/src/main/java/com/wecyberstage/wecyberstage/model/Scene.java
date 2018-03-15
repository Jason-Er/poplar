package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.SceneEntity;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class Scene extends SceneEntity {
    public List<Line> stageLines;
    public List<LineLocal> stageLinesLocal;
}
