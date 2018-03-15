package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/14.
 */

@Entity(tableName = "play",
        foreignKeys = {
                @ForeignKey(entity = StageEntity.class, parentColumns = "id", childColumns = "stage_id"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "director_id")
        },
        indices = {
                @Index("play_id"),
                @Index("director_id"),
                @Index("stage_id")
        })
public class PlayEntity {
    @PrimaryKey
    public long id;

    public String name;

    @ColumnInfo(name = "poster_url")
    public String posterURL;

    @ColumnInfo(name = "brief_intro")
    public String briefIntro;

    @ColumnInfo(name = "play_id")
    public long playId;

    @ColumnInfo(name = "director_id")
    public long directorId;

    @ColumnInfo(name = "stage_id")
    public long stageId;

    @ColumnInfo(name = "copy_from")
    public long copyfrom;
}
