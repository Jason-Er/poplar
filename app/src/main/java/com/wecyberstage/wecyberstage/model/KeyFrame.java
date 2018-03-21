package com.wecyberstage.wecyberstage.model;

import android.graphics.Point;
import android.media.Image;

import java.util.List;
import java.util.Map;

/**
 * Created by mike on 2018/3/15.
 */

public class KeyFrame {

    public static class LinePosition {
        public Point point;
        public Line line;

        public LinePosition(Point point, Line line) {
            this.point = point;
            this.line = line;
        }
    }

    public static class RolePosition {
        public Point point;
        public Role role;

        public RolePosition(Point point, Role role) {
            this.point = point;
            this.role = role;
        }
    }

    public Image background;
    public List<LinePosition> linePositions;
    public List<RolePosition> rolePositions;
}

