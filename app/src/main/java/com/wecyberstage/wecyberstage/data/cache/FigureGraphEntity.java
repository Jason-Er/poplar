package com.wecyberstage.wecyberstage.data.cache;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by mike on 2018/3/19.
 */

@Entity(tableName = "figure_graph",
        foreignKeys = {
                @ForeignKey(entity = FigureEntity.class, parentColumns = "id", childColumns = "figure_id")
        },
        indices = {
                @Index(value = {"figure_id"})
        })
public class FigureGraphEntity {
    @PrimaryKey
    public long id;

    @ColumnInfo(name = "figure_id")
    public long figureId;

    public long ordinal;

    @ColumnInfo(name = "graph_url")
    public String graphURL;
}
