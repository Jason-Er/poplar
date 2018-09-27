package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/19.
 */

@Entity(tableName = "mask_graph",
        foreignKeys = {
                @ForeignKey(entity = MaskEntity.class, parentColumns = "id", childColumns = "mask_id")
        },
        indices = {
                @Index(value = {"mask_id"})
        })
public class MaskGraphEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "mask_id")
    public long maskId;

    public String description;

    @ColumnInfo(name = "graph_url")
    public String graphURL;
}
