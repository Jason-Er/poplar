package com.wecyberstage.wecyberstage.model;

import java.util.List;

public class ComposeScript {

    public static class Line {
        public long roleId;
        public long graphOrdinal;
        public String graphURL;
        public String dialogue;

        public Line(long roleId, long graphOrdinal, String graphURL, String dialogue) {
            this.roleId = roleId;
            this.graphOrdinal = graphOrdinal;
            this.graphURL = graphURL;
            this.dialogue = dialogue;
        }
    }

    public long playId;
    public long sceneId;
    public List<Line> lineList;
}
