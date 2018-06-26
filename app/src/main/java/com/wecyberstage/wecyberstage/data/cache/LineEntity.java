package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/15.
 */

@Entity(tableName = "line",
        foreignKeys = {
                @ForeignKey(entity = SceneEntity.class, parentColumns = "id", childColumns = "scene_id"),
                @ForeignKey(entity = RoleEntity.class, parentColumns = "id", childColumns = "role_id")
        },
        indices = {
                @Index("scene_id"),
                @Index("role_id")
        })
public class LineEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "scene_id")
    public long sceneId;

    @ColumnInfo(name = "role_id")
    public long roleId;
    public long ordinal;

    public LineType type;
    @ColumnInfo(name = "direction")
    public String direction;
    public String dialogue;

    @ColumnInfo(name = "begin_time") // offset to ordinal time
    public float beginTime;

    @ColumnInfo(name = "audio_url")
    public String audioURL;
}
