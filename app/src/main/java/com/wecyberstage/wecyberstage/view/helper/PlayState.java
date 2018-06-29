package com.wecyberstage.wecyberstage.view.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayState implements Parcelable {
    private long playId;
    private long sceneId;
    private long startTime; // Millisecond

    public PlayState(long playId, long sceneId, long startTime) {
        this.playId = playId;
        this.sceneId = sceneId;
        this.startTime = startTime;
    }

    protected PlayState(Parcel in) {
        playId = in.readLong();
        sceneId = in.readLong();
        startTime = in.readLong();
    }

    public static final Creator<PlayState> CREATOR = new Creator<PlayState>() {
        @Override
        public PlayState createFromParcel(Parcel in) {
            return new PlayState(in);
        }

        @Override
        public PlayState[] newArray(int size) {
            return new PlayState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(playId);
        dest.writeLong(sceneId);
        dest.writeLong(startTime);
    }

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public long getSceneId() {
        return sceneId;
    }

    public void setSceneId(long sceneId) {
        this.sceneId = sceneId;
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

        PlayState playState = (PlayState) obj;

        if ( playId != playState.playId || sceneId != playState.sceneId || startTime != playState.startTime ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (31 * playId + 15 * sceneId + startTime);
        return result;
    }
}

