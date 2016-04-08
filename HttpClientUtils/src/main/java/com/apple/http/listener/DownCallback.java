package com.apple.http.listener;


import com.apple.http.common.BaseHttpClient;

/**
 * 下载文件请求返回
 * @author hushaoping
 */
public abstract class DownCallback extends BaseCallback{

    @Override
    public void onSuccess(String content, BaseHttpClient object, Object parse) {

    }

    @Override
    public void onError(Throwable error,BaseHttpClient client) {

    }

    @Override
    public void uploadProgress(long bytesRead, long contentLength, boolean done) {

    }
}
