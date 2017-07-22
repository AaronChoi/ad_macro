package com.aaron.application.ssmarket_ad.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitManager {
    private static Retrofit retrofit;

    private RetrofitManager(String host) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        retrofit = new Retrofit.Builder().baseUrl(host)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance(String host) {
        new RetrofitManager(host);
        return retrofit;
    }
}
