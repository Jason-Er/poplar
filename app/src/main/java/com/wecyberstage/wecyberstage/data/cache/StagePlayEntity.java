package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stage_play",
        foreignKeys = {
                @ForeignKey(entity = PlayEntity.class, parentColumns = "id", childColumns = "adapted_from"),
                @ForeignKey(entity = StageEntity.class, parentColumns = "id", childColumns = "stage_id"),
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "director_id")
        },
        indices = {
                @Index("director_id"),
                @Index("stage_id"),
                @Index("adapted_from")
        })
public class StagePlayEntity {
    @PrimaryKey
    public long id;

    public String name;

    @ColumnInfo(name = "director_id")
    public long directorId;

    @ColumnInfo(name = "stage_id")
    public long stageId;

    @ColumnInfo(name = "adapted_from")
    public long adaptedFrom;
}
