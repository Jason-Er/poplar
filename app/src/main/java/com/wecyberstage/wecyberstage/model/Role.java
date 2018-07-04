package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.RoleEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class Role extends RoleEntity {
    public Mask mask;
    public Role(long id, Mask mask) {
        this.id = id;
        this.mask = mask;
    }
}
