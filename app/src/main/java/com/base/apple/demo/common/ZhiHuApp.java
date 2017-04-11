package com.base.apple.demo.common;

import android.app.Application;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.HttpConfiguration;

/**
 * Created by kelin on 16-4-12.
 */
public class ZhiHuApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpConfiguration.Builder configuration = new HttpConfiguration.Builder(getApplicationContext());
        configuration.retryOnConnectionFailure(true);
        configuration.diskCacheSize(1000 * 1024);
        configuration.connectTimeout(15);
        configuration.readTimeout(15);
        configuration.writeTimeout(15);
        //  configuration.setBaseUrl("https://api.jucaicat.com/v2/");
        configuration.diskCacheDir(getCacheDir());
        BaseHttpClient.getBaseClient().init(configuration.build());
    }


}
