package com.wecyberstage.wecyberstage.model;

import java.util.ArrayList;
import java.util.List;

public class ComposeScript {

    public static class AvatarLine {
        private Avatar avatar;
        private Line line;

        public AvatarLine(Avatar avatar, Line line) {
            this.avatar = avatar;
            this.line = line;
        }

        public Avatar getAvatar() {
            return avatar;
        }

        public AvatarLine setAvatar(Avatar avatar) {
            this.avatar = avatar;
            return this;
        }

        public Line getLine() {
            return line;
        }

        public AvatarLine setLine(Line line) {
            this.line = line;
            return this;
        }

    }

    public static class Avatar {
        public long figureId;
        public long graphOrdinal;
        public String graphURL;

        public Avatar(long figureId, long graphOrdinal, String graphURL) {
            this.figureId = figureId;
            this.graphOrdinal = graphOrdinal;
            this.graphURL = graphURL;
        }
    }

    public static class Line {
        public long roleId;
        public String dialogue;
        public float startTime;
        public float duration;

        public Line(long roleId, String dialogue, float startTime, float duration) {
            this.roleId = roleId;
            this.dialogue = dialogue;
            this.startTime = startTime;
            this.duration = duration;
        }
    }

    public long playId;
    public long sceneId;
    public List<AvatarLine> avatarLines;

    public ComposeScript(long playId, long sceneId) {
        this.playId = playId;
        this.sceneId = sceneId;
        avatarLines = new ArrayList<>();
    }
}
