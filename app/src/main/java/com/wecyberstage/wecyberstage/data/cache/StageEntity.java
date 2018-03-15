package com.wecyberstage.wecyberstage.data.cache;

/**
 * Created by mike on 2018/3/15.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stage")
public class StageEntity {
    @PrimaryKey
    public long id;

    public float length;
    public float width;

    @ColumnInfo(name = "setting_height")
    public float settingHeight;
}
