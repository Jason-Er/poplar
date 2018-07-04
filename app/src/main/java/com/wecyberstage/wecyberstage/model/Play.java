package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.PlayEntity;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class Play extends PlayEntity{
    public User director;
    public Stage stage;
    public List<Scene> scenes;
    public List<Role> cast;
    public List<Mask> masks;
}
