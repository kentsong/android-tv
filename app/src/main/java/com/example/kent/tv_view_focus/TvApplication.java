package com.example.kent.tv_view_focus;

import android.app.Application;

import timber.log.Timber;

public class TvApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
