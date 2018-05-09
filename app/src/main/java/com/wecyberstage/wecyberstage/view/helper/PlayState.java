package com.wecyberstage.wecyberstage.view.helper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

public class PlayState implements Parcelable {
    long playId;
    long sceneId;
    long startTime; // Millisecond

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

}

