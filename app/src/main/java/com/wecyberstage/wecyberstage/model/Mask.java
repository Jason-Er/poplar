package com.wecyberstage.wecyberstage.model;

import com.wecyberstage.wecyberstage.data.cache.MaskEntity;

import java.util.ArrayList;
import java.util.List;

public class Mask extends MaskEntity {
    public List<MaskGraph> maskGraphList;

    public Mask(long id) {
        this.id = id;
        this.maskGraphList = new ArrayList<>();
    }
}
