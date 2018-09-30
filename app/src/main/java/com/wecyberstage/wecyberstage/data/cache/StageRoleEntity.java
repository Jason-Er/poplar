package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stage_role",
        foreignKeys = {
                @ForeignKey(entity = StagePlayEntity.class, parentColumns = "id", childColumns = "play_id"),
                @ForeignKey(entity = RoleEntity.class, parentColumns = "id", childColumns = "adapted_from"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "actor_id"),
                @ForeignKey(entity = MaskEntity.class, parentColumns = "id", childColumns = "mask_id")
        },
        indices = {
                @Index("play_id"),
                @Index("adapted_from"),
                @Index("actor_id"),
                @Index("mask_id")
        })
public class StageRoleEntity extends RoleEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "play_id")
    public long playId;

    @ColumnInfo(name = "actor_id")
    public long actorId;

    @ColumnInfo(name = "mask_id")
    public long maskId;

    @ColumnInfo(name = "adapted_from")
    public long adaptedFrom;

    // TODO: 9/26/2018 need test if real need this field for it has extends RoleEntity
    @ColumnInfo(name = "name")
    public String name;

    public String description;
}
