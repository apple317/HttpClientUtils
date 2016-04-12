package com.apple.http.listener;


import com.apple.http.entity.DownEntity;

/**
 *
 * 网络上传请求返回进度监听
 *
 * @author hushaoping
 */
public abstract class UploadCallback extends BaseCallback {
    @Override
    public void downProgress(DownEntity entity) {

    }
}
