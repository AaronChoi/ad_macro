package com.aaron.application.ssmarket_ad.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

@org.simpleframework.xml.Root(strict = false)
public class Root {
    @Element
    private int result_code;

    @Element(required = false)
    private LoginInfo info;

    @ElementList(name = "ArrayOfItems", required = false)
    private ArrayList<CategoryItem> items;

    public int getResult_code() {
        return result_code;
    }

    public LoginInfo getInfo() {
        return info;
    }

    public ArrayList<CategoryItem> getItems() {
        return items;
    }
}
