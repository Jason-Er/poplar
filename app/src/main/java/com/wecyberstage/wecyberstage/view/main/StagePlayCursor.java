package com.wecyberstage.wecyberstage.view.main;

import android.os.Parcel;
import android.os.Parcelable;

public class StagePlayCursor implements Parcelable {
    private long playId;
    private int ordinal; // scene ordinal
    private long startTime; // Millisecond

    public StagePlayCursor(long playId, int ordinal, long startTime) {
        this.playId = playId;
        this.ordinal = ordinal;
        this.startTime = startTime;
    }

    protected StagePlayCursor(Parcel in) {
        playId = in.readLong();
        ordinal = in.readInt();
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
        dest.writeInt(ordinal);
        dest.writeLong(startTime);
    }

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public float getStartTime() {
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

        if ( playId != stagePlayCursor.playId || ordinal != stagePlayCursor.ordinal || startTime != stagePlayCursor.startTime ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (31 * playId + 15 * ordinal + startTime);
        return result;
    }
}

