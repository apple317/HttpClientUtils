package com.base.apple.demo.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.apple.app.BaseDialog;
import com.apple.http.common.BaseHttpClient;


/**
 * 所有dialog 基础类定义
 */
public  class AppBaseDialog extends BaseDialog {

    private  Activity activity;

    public AppBaseDialog(Activity activity, int style) {
        super(activity, style);
        this.activity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView(savedInstanceState));
        initData(activity,savedInstanceState);
        initLisitener();
    }

    @Override
    protected void onSuccess(String content, BaseHttpClient client, Object parse) {

    }

    @Override
    public View initView(Bundle bundle) {
        return super.initView(bundle);
    }

    @Override
    protected void onError(Throwable error, BaseHttpClient client) {

    }

    @Override
    protected void initLisitener() {

    }


    @Override
    protected void initData(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    protected void initStyle() {

    }
}
