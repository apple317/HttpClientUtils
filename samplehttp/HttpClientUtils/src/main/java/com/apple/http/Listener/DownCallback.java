package com.apple.http.listener;




/**
 * 下载文件请求返回
 * @author hushaoping
 */
public abstract class DownCallback extends BaseCallback{

    @Override
    public void onSuccess(String content, Object object, String reqType) {

    }

    @Override
    public void onError(Throwable error, String content, String reqType) {

    }

    @Override
    public void uploadProgress(long bytesRead, long contentLength, boolean done) {

    }
}
