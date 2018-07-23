package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/18.
 */

@Entity(tableName = "mask",
        foreignKeys = {
                @ForeignKey(entity = UserEntity.class, parentColumns = "id", childColumns = "author_id")
        },
        indices = {
                @Index(value = {"author_id"})
        })
public class MaskEntity {
    @PrimaryKey
    public long id;

    public String name;

    @ColumnInfo(name = "author_id")
    public long authorId;
}
