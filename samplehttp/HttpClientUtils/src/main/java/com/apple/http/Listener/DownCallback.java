package com.apple.http.Listener;

import com.apple.http.entity.DownEntity;


/**
 * 下载文件请求返回
 * @author hushaoping
 */
public interface DownCallback {
    /**
     * 下载返回实体类
     * @param entity
     */
    void onProgress(DownEntity entity);
}
