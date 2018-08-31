package com.wecyberstage.wecyberstage.model;

public interface UpdateStagePlayInterface {
    void updateStageLine(StageLine stageLine);
    void addStageLine(StageLine stageLine);
    void deleteStageLine(StageLine stageLine);
    void swapStageLines(int position1, int position2);
}
