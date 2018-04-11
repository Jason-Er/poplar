package com.wecyberstage.wecyberstage.model;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class KeyFrame {

    public static class LineInfo {
        public PointF point;
        public Line line;

        public LineInfo(PointF point, Line line) {
            this.point = point;
            this.line = line;
        }
    }

    public static class PropInfo {
        public Prop prop;
        public RectF propViewRect;

        public PropInfo(Prop prop, RectF propViewRect) {
            this.prop = prop;
            this.propViewRect = propViewRect;
        }
    }

    public static class RoleInfo {
        public Role role;
        public RectF roleViewRect;
        public int selectedFigureGraphOrdinal;

        public RoleInfo(Role role, RectF roleViewRect, int selectedFigureGraphOrdinal) {
            this.role = role;
            this.roleViewRect = roleViewRect;
            this.selectedFigureGraphOrdinal = selectedFigureGraphOrdinal;
        }
    }

    public static class StageInfo {
        public String settingURL;
    }

    public StageInfo stageInfo;
    public List<LineInfo> lineInfoList;
    public List<RoleInfo> roleInfoList;
    public List<PropInfo> propInfoList;
}

