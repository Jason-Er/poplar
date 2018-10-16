package com.wecyberstage.wecyberstage.view.main;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class StagePlayCursor implements Parcelable {
    private long playId;
    private int sceneOrdinal; // scene ordinal
    private int lineOrdinal; // start position of stageLine
    private long startTime; // Millisecond

    public StagePlayCursor(long playId, int sceneOrdinal, int lineOrdinal, long startTime) {
        this.playId = playId;
        this.sceneOrdinal = sceneOrdinal;
        this.lineOrdinal = lineOrdinal;
        this.startTime = startTime;
    }

    protected StagePlayCursor(Parcel in) {
        playId = in.readLong();
        sceneOrdinal = in.readInt();
        lineOrdinal = in.readInt();
        startTime = in.readLong();
    }

    public static final Creator<StagePlayCursor> CREATOR = new Creator<StagePlayCursor>() {
        @Override
        public StagePlayCursor createFromParcel(Parcel in) {
            return new StagePlayCursor(in);
        }

        @Override
        public StagePlayCursor[] newArray(int size) {
            return new StagePlayCursor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(playId);
        dest.writeInt(sceneOrdinal);
        dest.writeInt(lineOrdinal);
        dest.writeLong(startTime);
    }

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public int getSceneOrdinal() {
        return sceneOrdinal;
    }

    public void setSceneOrdinal(int sceneOrdinal) {
        this.sceneOrdinal = sceneOrdinal;
    }

    public int getLineOrdinal() {
        return lineOrdinal;
    }

    public void setLineOrdinal(int lineOrdinal) {
        this.lineOrdinal = lineOrdinal;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        StagePlayCursor stagePlayCursor = (StagePlayCursor) obj;

        if ( playId != stagePlayCursor.playId || sceneOrdinal != stagePlayCursor.sceneOrdinal || lineOrdinal != stagePlayCursor.lineOrdinal || startTime != stagePlayCursor.startTime ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (31 * playId + 15 * sceneOrdinal + lineOrdinal);
        return result;
    }
}

