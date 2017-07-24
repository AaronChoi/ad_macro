package com.aaron.application.ssmarket_ad.network.service;

import com.aaron.application.ssmarket_ad.network.model.AdRoot;
import com.aaron.application.ssmarket_ad.network.model.Root;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by AaronMac on 2017. 7. 24..
 */

public interface AdInfo {
    @FormUrlEncoded
    @POST("/ssmarket/get_dome_goods_ad_info.php")
    Call<AdRoot> requestGoodsAdInfo (
            @Field("wgIdx") long wgIdx,
            @Field("userid") String userid,
            @Field("uuid") String uuid,
            @Field("oauth_token") String oauth_token
    );

    @FormUrlEncoded
    @POST("/ssmarket/query_dome_request_ad.php")
    Call<AdRoot> requestAdvertise (
            @Field("wgIdx") long wgIdx,
            @Field("applyDays") String applyDays,
            @Field("userid") String userid,
            @Field("uuid") String uuid,
            @Field("oauth_token") String oauth_token
    );
}
