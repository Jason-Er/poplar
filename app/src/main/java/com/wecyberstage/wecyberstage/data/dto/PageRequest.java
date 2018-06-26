package com.wecyberstage.wecyberstage.data.dto;

/**
 * Created by mike on 2018/3/13.
 */

public class PageRequest {
    public long page;
    public long size;
    public String sort;

    public PageRequest(long page, long size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}