package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;

@org.simpleframework.xml.Root(strict = false)
public class Goods {
    @Element(required = false)
    private int day;

    @Element(required = false)
    private String dateTitle;

    public int getDay() {
        return day;
    }

    public String getDateTitle() {
        return dateTitle;
    }
}
