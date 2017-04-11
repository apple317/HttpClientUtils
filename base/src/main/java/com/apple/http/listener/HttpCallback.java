package com.apple.http.listener;


import com.apple.http.entity.DownEntity;

/**
 *
 * 普通请求返回成功失败数据
 * @author hushaoping
 */
public abstract class HttpCallback extends BaseCallback {
    @Override
    public void uploadProgress(long bytesRead, long contentLength, boolean done) {

    }

    @Override
    public void downProgress(DownEntity entity) {

    }
}
