package com.aaron.application.ssmarket_ad.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.aaron.application.ssmarket_ad.BaseApplication;
import com.aaron.application.ssmarket_ad.R;
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
    private PreferenceManager pref;
    private AdType adType;
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
        pref = PreferenceManager.getInstance(this);
        infoService = RetrofitManager.getInstance(Constant.likeThatHost).create(AdInfo.class);

        if(intent != null) {
            adType = AdType.getType(intent.getStringExtra("adType"));
            Log.d("MacroService", "adType : " + adType);
        }
        requestCategoryInfo();
    }

    private void requestCategoryInfo() {
        Log.d("MacroService", "request category start");

        infoService.requestGoodsAdInfo(Long.parseLong(pref.getString(adType.getPrefKey(), "")),
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
                                    Log.d("MacroService", "request category is closed.. retry..");
                                    requestCategoryInfo();
                                }
                            } else {
                                Log.w("MacroService", "request category failed with body error, retry...");
                                requestCategoryInfo();
                            }
                        } else {
                            Log.w("MacroService", "request category failed with response error, retry...");
                            requestCategoryInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdRoot> call, Throwable t) {
                        Log.e("MacroService", "request category failed with network error, retry... :::" + t.getMessage());
                        requestCategoryInfo();
                    }
                });
    }

    private void requestAdvertise(String applyDays) {
        infoService.requestAdvertise(Long.parseLong(pref.getString(adType.getPrefKey(), "")), applyDays, Constant.userId, Constant.uuid, BaseApplication.TOKEN)
                .enqueue(new Callback<AdRoot>() {
                    @Override
                    public void onResponse(Call<AdRoot> call, Response<AdRoot> response) {
                        if(response.isSuccessful()) {
                            if(response.body() != null && response.body().getResult_code() != -1) {
                                // call receiver intent to main activity
                                Log.d("MacroService", "request ad success");
                                handleNotificationMessage(true, "");
                            } else {
                                Log.e("MacroService", "request ad failed with result code ::: " + response.body().getResult_code());
                                handleNotificationMessage(false, "result_code = " + response.body().getResult_code());
                            }
                        } else {
                            Log.e("MacroService", "request ad failed with response fail");
                            handleNotificationMessage(false, "response not success..");
                        }
                    }

                    @Override
                    public void onFailure(Call<AdRoot> call, Throwable t) {
                        Log.e("MacroService", "request ad failed with error ::: " + t.getMessage());
                        handleNotificationMessage(false, "error : " + t.getMessage());
                    }
                });
    }

    private void handleNotificationMessage(boolean success, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(getApplicationInfo().icon)
                .setContentTitle("광고 신청 " + (success ? "완료" : "실패"))
                .setAutoCancel(true)
                .setContentText(success ? "광고 Type : " + adType.getTypeName() : "실패원인 : " + message);

        notificationManager.notify(adType.getRequestCode(), builder.build());
    }
}
