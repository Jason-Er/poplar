package com.wecyberstage.wecyberstage.util.character;

import com.wecyberstage.wecyberstage.util.privilege.Privilege;

import java.util.Map;

public abstract class Character4Play {

    protected Map<String, Privilege> mapPrivileges;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
