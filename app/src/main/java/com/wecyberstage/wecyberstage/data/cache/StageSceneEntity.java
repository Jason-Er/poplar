package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stage_scene",
        foreignKeys = {
                @ForeignKey(entity = StagePlayEntity.class, parentColumns = "id", childColumns = "play_id"),
                @ForeignKey(entity = SceneEntity.class, parentColumns = "id", childColumns = "adapted_from"),
                @ForeignKey(entity = ActionScriptEntity.class, parentColumns = "id", childColumns = "action_id"),
                @ForeignKey(entity = SettingEntity.class, parentColumns = "id", childColumns = "setting_id"),
                @ForeignKey(entity = MusicEntity.class, parentColumns = "id", childColumns = "bgm_id")
        },
        indices = {
                @Index("play_id"),
                @Index("adapted_from"),
                @Index("action_id"),
                @Index("setting_id"),
                @Index("bgm_id")
        })
public class StageSceneEntity {
    @PrimaryKey
    public long id;

    public String name;

    public long ordinal;

    @ColumnInfo(name = "play_id")
    public long playId;

    @ColumnInfo(name = "action_id")
    public long actionScriptId;

    @ColumnInfo(name = "setting_id")
    public long settingId;

    @ColumnInfo(name = "bgm_id")
    public long backgroundMusicId;

    @ColumnInfo(name = "adapted_from")
    public long adaptedFrom;
}
