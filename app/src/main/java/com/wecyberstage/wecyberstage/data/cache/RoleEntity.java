package com.wecyberstage.wecyberstage.data.cache;

/**
 * Created by mike on 2018/3/15.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stageRole",
        foreignKeys = {
                @ForeignKey(entity = PlayEntity.class, parentColumns = "id", childColumns = "play_id")
        },
        indices = {
                @Index("play_id")
        })
public class RoleEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public String description;

    @ColumnInfo(name = "play_id")
    public long playId;
}
