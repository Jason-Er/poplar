package com.wecyberstage.wecyberstage.model;

public interface StageLineHandle {
    void updateStageLine(StageLine stageLine);
    void addStageLine(StageLine stageLine);
    void deleteStageLine(StageLine stageLine);
    void swapStageLines(int position1, int position2);
}
