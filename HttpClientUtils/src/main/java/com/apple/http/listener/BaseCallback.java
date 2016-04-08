package com.apple.http.listener;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.entity.DownEntity;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 请求callback;
 *
 * @author hushaoping
 */
public abstract class BaseCallback{
    /**
     * 请求成功
     * @param content 返回值
     * @param object  返回的转化对象
     * @param parse 解析对象
     */
    public abstract void onSuccess(String content, BaseHttpClient object, Object parse);

    /**
     * 请求失败
     *
     * @param error   错误
     * @param client client发起者
     */
    public abstract void onError(Throwable error, BaseHttpClient client);

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
        public void onSuccess(String content, BaseHttpClient client, Object parse) {

        }

        @Override
        public void onError(Throwable error, BaseHttpClient client) {

        }

        @Override
        public void uploadProgress(long bytesRead, long contentLength, boolean done) {

        }

        @Override
        public void downProgress(DownEntity entity) {

        }
    };
}
