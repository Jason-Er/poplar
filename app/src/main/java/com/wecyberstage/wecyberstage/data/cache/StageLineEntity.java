package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stage_line",
        foreignKeys = {
                @ForeignKey(entity = LineEntity.class, parentColumns = "id", childColumns = "adapted_from"),
                @ForeignKey(entity = StageSceneEntity.class, parentColumns = "id", childColumns = "scene_id"),
                @ForeignKey(entity = StageRoleEntity.class, parentColumns = "id", childColumns = "role_id"),
                @ForeignKey(entity = MaskGraphEntity.class, parentColumns = "id", childColumns = "maskGraph_id"),
                @ForeignKey(entity = VoiceEntity.class, parentColumns = "id", childColumns = "voice_id")
        },
        indices = {
                @Index("adapted_from"),
                @Index("scene_id"),
                @Index("role_id"),
                @Index("maskGraph_id"),
                @Index("voice_id")
        })
public class StageLineEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "scene_id")
    public long sceneId;
    public long ordinal;

    public LineType type;
    @ColumnInfo(name = "direction")
    public String direction;
    public String dialogue;

    @ColumnInfo(name = "role_id")
    public long roleId;

    @ColumnInfo(name = "maskGraph_id")
    public long maskGraphId;

    @ColumnInfo(name = "voice_id")
    public long voiceId;

    @ColumnInfo(name = "begin_time") // millisecond
    public long beginTime;

    @ColumnInfo(name = "adapted_from")
    public long adaptedFrom;
}
