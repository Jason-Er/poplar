package com.wecyberstage.wecyberstage.data.cache;

/**
 * Created by mike on 2018/3/15.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "role",
        foreignKeys = {
                @ForeignKey(entity = PlayEntity.class, parentColumns = "id", childColumns = "play_id"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "actor_id"),
                @ForeignKey(entity = MaskEntity.class, parentColumns = "id", childColumns = "mask_id")
        },
        indices = {
                @Index("actor_id"),
                @Index("play_id"),
                @Index("figure_id")
        })
public class RoleEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public String description;

    @ColumnInfo(name = "actor_id")
    public long actorId;

    @ColumnInfo(name = "play_id")
    public long playId;

    @ColumnInfo(name = "mask_id")
    public long maskId;
}
