package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;

public class AdInfo {
    @Element(required = false)
    private long wgIdx;

    @Element(required = false)
    private String title;

    @Element(required = false)
    private String status;

    @Element(required = false)
    private String price;

    @Element(required = false)
    private String msg;

    @Element(required = false)
    private String adInfoUrl;

    @Element(required = false)
    private int adRequestCount;

    @Element(required = false)
    private int adRequestMaxCount;

    @Element(required = false)
    private String appliedDays;

    public long getWgIdx() {
        return wgIdx;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }

    public String getMsg() {
        return msg;
    }

    public String getAdInfoUrl() {
        return adInfoUrl;
    }

    public int getAdRequestCount() {
        return adRequestCount;
    }

    public int getAdRequestMaxCount() {
        return adRequestMaxCount;
    }

    public String getAppliedDays() {
        return appliedDays;
    }
}
