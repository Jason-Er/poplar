package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.MaskGraphEntity;

/**
 * Created by mike on 2018/3/21.
 */

public class MaskGraph extends MaskGraphEntity {
    public MaskGraph(long maskId, String graphURL) {
        this.maskId = maskId;
        this.graphURL = graphURL;
    }
}
