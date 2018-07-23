package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "music",
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "author_id")
        },
        indices = {
                @Index(value = {"author_id"})
        })
public class MusicEntity {
    @PrimaryKey
    public long id;

    public String name;

    @ColumnInfo(name = "author_id")
    public long authorId;

    @ColumnInfo(name = "res_url")
    public String musicURL;
}
