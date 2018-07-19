package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "voice",
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "user_id"),
                @ForeignKey(entity = StageLineEntity.class, parentColumns = "id", childColumns = "line_id")
        },
        indices = {
                @Index(value = {"line_id", "user_id"})
        })
public class VoiceEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "user_id")
    public long userId;

    @ColumnInfo(name = "line_id")
    public long lineId;

    @ColumnInfo(name = "res_url")
    public String audioURL;
}
