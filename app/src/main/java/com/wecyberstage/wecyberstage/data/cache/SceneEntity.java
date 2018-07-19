package com.wecyberstage.wecyberstage.data.cache;

/**
 * Created by mike on 2018/3/15.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "scene",
        foreignKeys = {
                @ForeignKey(entity = PlayEntity.class, parentColumns = "id", childColumns = "play_id")
        },
        indices = {
                @Index("play_id")
        })
public class SceneEntity {
    @PrimaryKey
    public long id;

    public String name;
    @ColumnInfo(name = "act_ordinal")
    public long actOrdinal;
    public long ordinal;

    @ColumnInfo(name = "play_id")
    public long playId;

    public String setting;
    @ColumnInfo(name = "at_rise")
    public String atrise;
}
