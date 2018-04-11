package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/15.
 */

@Entity(tableName = "user",
        indices = {@Index(value = "e_mail", unique = true)})
public class UserEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "e_mail")
    public String email;

    public String password;
}
