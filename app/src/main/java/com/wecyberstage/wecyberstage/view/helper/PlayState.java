package com.wecyberstage.wecyberstage.view.helper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

public class PlayState implements Parcelable {
    private long playId;
    private long sceneId;
    private float startTime; // Millisecond

    public PlayState(long playId, long sceneId, float startTime) {
        this.playId = playId;
        this.sceneId = sceneId;
        this.startTime = startTime;
    }

    protected PlayState(Parcel in) {
        playId = in.readLong();
        sceneId = in.readLong();
        startTime = in.readFloat();
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
        dest.writeFloat(startTime);
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

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }
}

