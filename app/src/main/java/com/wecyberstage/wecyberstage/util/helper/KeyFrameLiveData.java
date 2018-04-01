package com.wecyberstage.wecyberstage.util.helper;

import android.arch.lifecycle.LiveData;
import android.graphics.PointF;
import android.graphics.RectF;

import com.wecyberstage.wecyberstage.data.repository.PlayRepository;
import com.wecyberstage.wecyberstage.model.KeyFrame;
import com.wecyberstage.wecyberstage.model.Line;
import com.wecyberstage.wecyberstage.model.Prop;
import com.wecyberstage.wecyberstage.model.Role;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by mike on 2018/3/15.
 */

@Singleton
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

        keyFrame.roleInfoList = new ArrayList<>();
        keyFrame.lineInfoList = new ArrayList<>();
        keyFrame.propInfoList = new ArrayList<>();
        keyFrame.stageInfo = new KeyFrame.StageInfo();

        Role role = new Role();
        role.firstName = "jason";
        role.lastName = "E";
        Line line = new Line();
        line.dialogue = "Hello world!";
        PointF point = new PointF(200,200);
        RectF roleViewRect = new RectF(0.2f,0.2f,0.4f,0.4f);

        Prop prop = new Prop();
        RectF propViewRect = new RectF(0,0,40,40);

        KeyFrame.RoleInfo roleInfo = new KeyFrame.RoleInfo(role, roleViewRect,0);
        KeyFrame.LineInfo lineInfo = new KeyFrame.LineInfo(point, line);
        KeyFrame.PropInfo propInfo = new KeyFrame.PropInfo(prop, propViewRect);

        keyFrame.roleInfoList.add(roleInfo);
        keyFrame.lineInfoList.add(lineInfo);
        keyFrame.propInfoList.add(propInfo);
        keyFrame.stageInfo.settingURL = "http://pic28.photophoto.cn/20130727/0035035114302168_b.jpg";

        setValue(keyFrame);
    }
}
