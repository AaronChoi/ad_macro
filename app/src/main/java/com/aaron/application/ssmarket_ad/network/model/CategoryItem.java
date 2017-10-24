package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;

@org.simpleframework.xml.Root(name = "item", strict = false)
public class CategoryItem {
    @Element(required = false)
    private long idx;
    @Element(required = false)
    private int category;
    @Element(required = false)
    private String name;
    @Element(required = false)
    private int good;
    @Element(required = false)
    private int hit;
    @Element(required = false)
    private String imageUrl;
    @Element(required = false)
    private String listImage;
    @Element(required = false)
    private String price;
    @Element(required = false)
    private int isOpenPrice;
    @Element(required = false)
    private int salesState;
    @Element(required = false)
    private int isDomeNotice;
    @Element(required = false)
    private int isSampleGoods;
    @Element(required = false)
    private int bgId;
    @Element(required = false)
    private int sgId;
    @Element(required = false)
    private int isSRAGoods;

    public long getIdx() {
        return idx;
    }

    public int getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getGood() {
        return good;
    }

    public int getHit() {
        return hit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getListImage() {
        return listImage;
    }

    public String getPrice() {
        return price;
    }

    public int getIsOpenPrice() {
        return isOpenPrice;
    }

    public int getSalesState() {
        return salesState;
    }

    public int getIsDomeNotice() {
        return isDomeNotice;
    }

    public int getIsSampleGoods() {
        return isSampleGoods;
    }

    public int getBgId() {
        return bgId;
    }

    public int getSgId() {
        return sgId;
    }

    public int getIsSRAGoods() {
        return isSRAGoods;
    }
}
