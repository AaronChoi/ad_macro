package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;

@org.simpleframework.xml.Root(strict = false)
public class LoginInfo {
    @Element(required = false)
    private String storeName;
    @Element(required = false)
    private String userName;
    @Element(required = false)
    private String memberPart;
    @Element(required = false)
    private String jobClass;
    @Element(required = false)
    private String setCategorys;
    @Element(required = false)
    private String setTags;
    @Element(required = false)
    private String wsIdx;
    @Element(required = false)
    private String rsIdx;
    @Element(required = false)
    private String status;
    @Element(required = false)
    private String country;
    @Element(required = false)
    private String isAllowed;
    @Element(required = false)
    private String oauth_token;
    @Element(required = false)
    private String certStatus;
    @Element(required = false)
    private String certMsg;
    @Element(required = false)
    private String certDDay;
    @Element(required = false)
    private String certLeftDays;
    @Element(required = false)
    private String orderPermission;
    @Element(required = false)
    private String uploadUrl;
    @Element(required = false)
    private String uploadUrlV2;
    @Element(required = false)
    private String resultMsg;
    @Element(required = false)
    private String storeImageUrl;
    @Element(required = false)
    private String location;
    @Element(required = false)
    private String tel;
    @Element(required = false)
    private int alarmCount;
    @Element(required = false)
    private int orderQuestionCount;
    @Element(required = false)
    private int goodsQuestionCount;
    @Element(required = false)
    private long tradeCount;
    @Element(required = false)
    private int tradeRequestCount;
    @Element(required = false)
    private int goodsCount;
    @Element(required = false)
    private String hitCount;
    @Element(required = false)
    private int tradeOpenGoodsCount;
    @Element(required = false)
    private int schoiceGoodsCount;
    @Element(required = false)
    private int curMonthAdStatus;
    @Element(required = false)
    private long plusTGid;
    @Element(required = false)
    private long plusAGid;
    @Element(required = false)
    private long plusBGid;
    @Element(required = false)
    private long plusCGid;
    @Element(required = false)
    private int todayPlusTStatus;
    @Element(required = false)
    private int todayPlusAStatus;
    @Element(required = false)
    private int todayPlusBStatus;
    @Element(required = false)
    private int todayPlusCStatus;
    @Element(required = false)
    private int newOrderCount;

    public String getStoreName() {
        return storeName;
    }

    public String getUserName() {
        return userName;
    }

    public String getMemberPart() {
        return memberPart;
    }

    public String getJobClass() {
        return jobClass;
    }

    public String getSetCategorys() {
        return setCategorys;
    }

    public String getSetTags() {
        return setTags;
    }

    public String getWsIdx() {
        return wsIdx;
    }

    public String getRsIdx() {
        return rsIdx;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getIsAllowed() {
        return isAllowed;
    }

    public String getOauth_token() {
        return oauth_token;
    }

    public String getCertStatus() {
        return certStatus;
    }

    public String getCertMsg() {
        return certMsg;
    }

    public String getCertDDay() {
        return certDDay;
    }

    public String getCertLeftDays() {
        return certLeftDays;
    }

    public String getOrderPermission() {
        return orderPermission;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public String getUploadUrlV2() {
        return uploadUrlV2;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public String getStoreImageUrl() {
        return storeImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getTel() {
        return tel;
    }

    public int getAlarmCount() {
        return alarmCount;
    }

    public int getOrderQuestionCount() {
        return orderQuestionCount;
    }

    public int getGoodsQuestionCount() {
        return goodsQuestionCount;
    }

    public long getTradeCount() {
        return tradeCount;
    }

    public int getTradeRequestCount() {
        return tradeRequestCount;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public String getHitCount() {
        return hitCount;
    }

    public int getTradeOpenGoodsCount() {
        return tradeOpenGoodsCount;
    }

    public int getSchoiceGoodsCount() {
        return schoiceGoodsCount;
    }

    public int getCurMonthAdStatus() {
        return curMonthAdStatus;
    }

    public long getPlusTGid() {
        return plusTGid;
    }

    public long getPlusAGid() {
        return plusAGid;
    }

    public long getPlusBGid() {
        return plusBGid;
    }

    public long getPlusCGid() {
        return plusCGid;
    }

    public int getTodayPlusTStatus() {
        return todayPlusTStatus;
    }

    public int getTodayPlusAStatus() {
        return todayPlusAStatus;
    }

    public int getTodayPlusBStatus() {
        return todayPlusBStatus;
    }

    public int getTodayPlusCStatus() {
        return todayPlusCStatus;
    }

    public int getNewOrderCount() {
        return newOrderCount;
    }
}
