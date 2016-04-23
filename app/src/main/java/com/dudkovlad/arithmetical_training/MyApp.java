package com.dudkovlad.arithmetical_training;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;

/**
 * Created by vlad on 05.04.2016.
 */
public class MyApp extends Application {
    public final static String YM_API_KEY = "6c06206e-d0c3-45f9-9dc5-c6203f9444c3";

    @Override
    public void onCreate(){
        super.onCreate();
        YandexMetrica.activate(getApplicationContext(), YM_API_KEY);
        YandexMetrica.enableActivityAutoTracking(this);
    }
}
