package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

@org.simpleframework.xml.Root(name = "root", strict = false)
public class AdRoot {
    @Element
    private int result_code;

    @Element(required = false)
    private AdInfo info;

    @ElementList(name = "ArrayOfItems", required = false)
    private ArrayList<Goods> items;

    public int getResult_code() {
        return result_code;
    }

    public AdInfo getInfo() {
        return info;
    }

    public ArrayList<Goods> getItems() {
        return items;
    }
}
