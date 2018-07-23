package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StageRoleEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class StageRole extends StageRoleEntity {
    public Mask mask;
    public StageRole(long id, Mask mask) {
        this.id = id;
        this.mask = mask;
    }
}
