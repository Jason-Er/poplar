package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ej on 10/16/2017.
 */
@Entity(tableName = "line_local",
        foreignKeys = {
                @ForeignKey(entity = LineEntity.class, parentColumns = "id", childColumns = "line_id"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "user_id")
        },
        indices = {
                @Index(value = {"line_id", "user_id"})
        })
public class LineLocalEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "audio_url")
    public String audioURL;

    @ColumnInfo(name = "line_id")
    public long lineId;
    @ColumnInfo(name = "user_id")
    public long userId;
}
