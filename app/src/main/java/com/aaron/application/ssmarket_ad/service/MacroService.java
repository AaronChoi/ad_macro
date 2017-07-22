package com.aaron.application.ssmarket_ad.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MacroService extends IntentService {

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
        requestCategoryItem();
    }

    private void requestCategoryItem() {

    }
}
