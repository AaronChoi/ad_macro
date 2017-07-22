package com.aaron.application.ssmarket_ad.network.service;

import com.aaron.application.ssmarket_ad.network.model.Root;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ItemList {
    @FormUrlEncoded
    @POST("/ssmarket/get_dome_my_goods_list")
    Call<Root> requestGoodsList (
            @Field("query_count") int query_count,
            @Field("nonpublic") int nonpublic,
            @Field("lcdIdx") int lcdIdx,
            @Field("display_count") int display_count,
            @Field("userid") String userid,
            @Field("sortBy") String sortBy,
            @Field("uuid") String uuid,
            @Field("oauth_token") String oauth_token
    );
}
