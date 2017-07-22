package com.aaron.application.ssmarket_ad.network.service;

import com.aaron.application.ssmarket_ad.network.model.Root;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Login {
    @FormUrlEncoded
    @POST("/ssmarket/login_member_v3.php")
    Call<Root> requestLogin (
            @Field("networkOperatorNumber") int networkOperatorNumber,
            @Field("networkOperatorName") String networkOperatorName,
            @Field("networkCountry") String networkCountry,
            @Field("id") String id,
            @Field("pw") String pw,
            @Field("uuid") String uuid
    );

    @GET("/ssmarket/get_dome_my_store_info_v2")
    Call<Root> requestStoreInfo(
            @Query("oauth_token") String oauth_token,
            @Query("userid") String userid,
            @Query("uuid") String uuid
    );
}
