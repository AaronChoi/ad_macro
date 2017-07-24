package com.aaron.application.ssmarket_ad.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.aaron.application.ssmarket_ad.BaseApplication;
import com.aaron.application.ssmarket_ad.common.AdType;
import com.aaron.application.ssmarket_ad.common.Constant;
import com.aaron.application.ssmarket_ad.common.PreferenceManager;
import com.aaron.application.ssmarket_ad.network.RetrofitManager;
import com.aaron.application.ssmarket_ad.network.model.AdRoot;
import com.aaron.application.ssmarket_ad.network.model.Goods;
import com.aaron.application.ssmarket_ad.network.service.AdInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MacroService extends IntentService {
    public static volatile boolean stopService = false;

    private PreferenceManager pref;
    private long wgIdx;
    private AdInfo infoService;

    public MacroService() {
        super("MacroService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MacroService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MacroService", "service started!!");
        stopService = false;
        pref = PreferenceManager.getInstance(this);
        infoService = RetrofitManager.getInstance(Constant.likeThatHost).create(AdInfo.class);

        if(intent != null) {
            switch (intent.getStringExtra("adType")) {
                case "T" :
                    wgIdx = Long.parseLong(pref.getString(PreferenceManager.PREFERENCE_AD_T, ""));
                    break;
                case "A" :
                    wgIdx = Long.parseLong(pref.getString(PreferenceManager.PREFERENCE_AD_A, ""));
                    break;
                case "B" :
                    wgIdx = Long.parseLong(pref.getString(PreferenceManager.PREFERENCE_AD_B, ""));
                    break;
                case "C" :
                    wgIdx = Long.parseLong(pref.getString(PreferenceManager.PREFERENCE_AD_C, ""));
                    break;
                default:
                    wgIdx = Long.parseLong(pref.getString(PreferenceManager.PREFERENCE_AD_T, ""));
                    break;
            }
        }
        requestCategoryInfo();
    }

    private void requestCategoryInfo() {
        if(stopService) {
            Log.d("MacroService", "stop service macro");
            stopSelf();
            return;
        }
        Log.d("MacroService", "request category start");

        infoService.requestGoodsAdInfo(wgIdx,
                        Constant.userId,
                        Constant.uuid,
                        BaseApplication.TOKEN)
                .enqueue(new Callback<AdRoot>() {
                    @Override
                    public void onResponse(Call<AdRoot> call, Response<AdRoot> response) {
                        if(response.isSuccessful()) {
                            if(response.body() != null && response.body().getInfo() != null) {
                                boolean isOpen = !"Closed".equalsIgnoreCase(response.body().getInfo().getStatus());
                                if(isOpen) {
                                    String applyDays = "";
                                    ArrayList<String> requestDays = pref.getStringArray(PreferenceManager.PREFERENCE_CALENDAR, null);
                                    ArrayList<Goods> serverDays = response.body().getItems();
                                    int count = 0;

                                    if(requestDays != null && serverDays != null && !serverDays.isEmpty()) {
                                        int date;
                                        for(String day : requestDays) {
                                            date = Integer.parseInt(day);
                                            if(date >= serverDays.size()) {
                                                break;
                                            }

                                            if(count != 0) {
                                                applyDays += ",";
                                            }
                                            applyDays += String.valueOf(serverDays.get(date).getDay());
                                            count++;
                                        }
                                    } else if(serverDays != null) {
                                        for (Goods day : serverDays) {
                                            if (count > 7) {
                                                break;
                                            }

                                            if (count != 0) {
                                                applyDays += ",";
                                            }
                                            applyDays += day.getDay();
                                            count++;
                                        }
                                    }
                                    requestAdvertise(applyDays);
                                } else {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    requestCategoryInfo();
                                }
                            } else {
                                Log.w("MacroService", "request category error, retry...");
                                requestCategoryInfo();
                            }
                        } else {
                            Log.w("MacroService", "request category error, retry...");
                            requestCategoryInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdRoot> call, Throwable t) {
                        requestCategoryInfo();
                    }
                });
    }

    private void requestAdvertise(final String applyDays) {
        infoService.requestAdvertise(wgIdx, applyDays, Constant.userId, Constant.uuid, BaseApplication.TOKEN)
                .enqueue(new Callback<AdRoot>() {
                    @Override
                    public void onResponse(Call<AdRoot> call, Response<AdRoot> response) {
                        if(response.isSuccessful()) {
                            if(response.body() != null && response.body().getResult_code() != -1) {
                                // call receiver intent to main activity
                                Log.d("MacroService", "request ad success");
                                LocalBroadcastManager.getInstance(MacroService.this).sendBroadcast(new Intent("action.request.ad.complete"));
                            } else {
                                Log.d("MacroService", "request ad failed");
                                requestAdvertise(applyDays);
                            }
                        } else {
                            Log.d("MacroService", "request ad failed");
                            requestAdvertise(applyDays);
                        }
                    }

                    @Override
                    public void onFailure(Call<AdRoot> call, Throwable t) {
                        requestAdvertise(applyDays);
                    }
                });
    }
}
