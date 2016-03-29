package com.apple.http.Listener;

/**
 * 请求callback;
 *
 * @author hushaoping
 */
public interface HttpCallback {
    /**
     * 请求成功
     *
     * @param content 返回值
     * @param object  返回的转化对象
     * @param reqType 请求的唯一识别
     */
    void onSuccess(String content, Object object, String reqType);

    /**
     * 请求失败
     *
     * @param error   错误
     * @param content 返回值
     * @param reqType 请求的唯一识别
     */
    void onFailure(Throwable error, String content, String reqType);

    void onProgress(long bytesRead, long contentLength, boolean done);
}
