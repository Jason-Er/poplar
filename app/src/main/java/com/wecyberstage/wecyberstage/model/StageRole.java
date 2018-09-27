package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.StageRoleEntity;

/**
 * Created by mike on 2018/3/15.
 */

public class StageRole extends StageRoleEntity {
    public Mask mask;
    public StageRole(long id, String name, Mask mask) {
        this.id = id;
        this.name = name;
        this.mask = mask;
    }

    public MaskGraph getMaskGraph(int ordinal) {
        return mask==null? null : mask.getMaskGraph(ordinal);
    }
}
