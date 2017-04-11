package com.base.apple.demo.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.app.BaseFragment;
import com.apple.http.common.BaseHttpClient;

/**
 * 我的界面
 * Created by android-dev on 15/5/14.
 */
@SuppressWarnings("ALL")
public   class AppBaseFragement extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater,container,savedInstanceState);
    }

    @Override
    protected void initLisitener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initStyle() {

    }

    @Override
    protected void onSuccess(String content, BaseHttpClient client, Object parse) {

    }

    @Override
    protected void onError(Throwable error, BaseHttpClient client) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
        initLisitener();
    }
}
