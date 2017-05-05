package com.apple.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.entity.DownEntity;
import com.apple.http.listener.HttpCallback;

/**
 * Created by kelin on 16-3-15.
 */
public abstract class ViewModel extends HttpCallback {

    @Override
    public void success(String content, BaseHttpClient client, Object parse) {

    }

    @Override
    public void error(Throwable error, BaseHttpClient client) {

    }

    @Override
    public void uploadProgress(long bytesRead, long contentLength, boolean done) {
        super.uploadProgress(bytesRead, contentLength, done);
    }

    @Override
    public void downProgress(DownEntity entity) {
        super.downProgress(entity);
    }


    /**
     * 界面重绘操作
     */
    public void onResume() {

    }


    /**
     * 界面停止操作
     */
    public void onStop() {

    }

    /**
     * 界面销毁
     */
    public void onDestory() {

    }


    /**
     * @deprecated 初始化数据
     */
    public abstract void initData(Bundle bundle);


    /**
     * @deprecated 初始化视图
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState);








}
