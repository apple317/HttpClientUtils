package com.apple.http.listener;


import com.apple.http.entity.DownEntity;
import com.apple.http.common.BaseHttpClient;

/**
 * 请求callback;
 *
 * @author hushaoping
 */
public abstract class BaseCallback{
    /**
     * 请求成功
     * @param content 返回值
     * @param client  返回的发起端对象
     * @param parse 解析对象
     */
    public abstract void success(String content, BaseHttpClient client, Object parse);

    /**
     * 请求失败
     *
     * @param error   错误
     * @param client client发起者
     */
    public abstract void error(Throwable error, BaseHttpClient client);

    /**
     * 文件上传进度
     * @param bytesRead
     * @param contentLength
     * @param done
     */

    public abstract void uploadProgress(long bytesRead, long contentLength, boolean done);

    /**
     * 文件下载进度返回
     * @param entity
     */
    public abstract void downProgress(DownEntity entity);

    public static BaseCallback CALLBACK_DEFAULT = new BaseCallback()
    {
        @Override
        public void success(String content, BaseHttpClient client, Object parse) {

        }

        @Override
        public void error(Throwable error, BaseHttpClient client) {

        }

        @Override
        public void uploadProgress(long bytesRead, long contentLength, boolean done) {

        }

        @Override
        public void downProgress(DownEntity entity) {

        }
    };
}
