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

        public PropInfo(Point point, Prop prop) {
            this.point = point;
            this.prop = prop;
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

    public String settingURL;
    public List<LineInfo> lineInfoList;
    public List<RoleInfo> roleInfoList;
    public List<PropInfo> propInfoList;
}

