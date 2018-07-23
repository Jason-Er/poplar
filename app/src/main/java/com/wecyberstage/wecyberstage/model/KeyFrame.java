package com.wecyberstage.wecyberstage.model;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by mike on 2018/3/15.
 */

public class KeyFrame {

    public static class LineInfo {
        public PointF point;
        public StageLine line;

        public LineInfo(PointF point, StageLine line) {
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
        public StageRole stageRole;
        public RectF roleViewRect;
        public int selectedFigureGraphOrdinal;

        public RoleInfo(StageRole stageRole, RectF roleViewRect, int selectedFigureGraphOrdinal) {
            this.stageRole = stageRole;
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

