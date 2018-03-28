package com.wecyberstage.wecyberstage.model;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class KeyFrame {

    public static class LineInfo {
        public Point point;
        public Line line;

        public LineInfo(Point point, Line line) {
            this.point = point;
            this.line = line;
        }
    }

    public static class PropInfo {
        public Point point;
        public Prop prop;
        public Rect propViewRect;

        public PropInfo(Point point, Prop prop, Rect propViewRect) {
            this.point = point;
            this.prop = prop;
            this.propViewRect = propViewRect;
        }
    }

    public static class RoleInfo {
        public Point point;
        public Role role;
        public Rect roleViewRect;
        public int selectedFigureGraphOrdinal;

        public RoleInfo(Point point, Role role, Rect roleViewRect, int selectedFigureGraphOrdinal) {
            this.point = point;
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

