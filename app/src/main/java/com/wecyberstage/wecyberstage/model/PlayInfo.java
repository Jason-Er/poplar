package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.PlayEntity;

import java.util.List;

/**
 * Created by mike on 2018/3/8.
 */

public class PlayInfo extends PlayEntity {
    public User director;
    public Stage stage;
    public List<Scene> scenes;
    public List<Role> cast;
}
