package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.graphics.Point;
import android.graphics.Rect;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.model.Line;
import com.wecyberstage.wecyberstage.model.Role;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mike on 2018/3/15.
 */

public class KeyFrameLiveData extends LiveData<KeyFrame> {
    @Inject
    public KeyFrameLiveData(PlayRepository repository) {

    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    public void setPlayId(long playId) {

        KeyFrame keyFrame = new KeyFrame();
        keyFrame.settingURL = null;

        keyFrame.roleInfoList = new ArrayList<>();
        keyFrame.lineInfoList = new ArrayList<>();

        Role role = new Role();
        role.firstName = "jason";
        role.lastName = "E";
        Line line = new Line();
        line.dialogue = "Hello world!";
        Point point = new Point(0,0);
        Rect roleViewRect = new Rect(0,0,40,40);
        KeyFrame.RoleInfo rolePosition = new KeyFrame.RoleInfo(point, role, roleViewRect,0);
        KeyFrame.LineInfo linePosition = new KeyFrame.LineInfo(point, line);

        keyFrame.roleInfoList.add(rolePosition);
        keyFrame.lineInfoList.add(linePosition);

        setValue(keyFrame);
    }
}
