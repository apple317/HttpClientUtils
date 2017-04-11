package com.apple.web;

import android.net.Uri;

/**
 *
 */
public interface IBridgeEngine extends IWebViewJavascriptBridge {


    /**
     * 是否为JS回调Scheme
     * @param url
     * @return
     */
    boolean isCorrectProcotocolScheme(Uri url);

    /**
     * 是否为初始化加载
     * @param url
     * @return
     */
    boolean isBridgeLoadedURL(Uri url);

    /**
     * 是否为获取消息url
     * @param url
     * @return
     */
//    boolean isQueueMessageURL(Uri url);

    /**
     * 注入JS引擎代码
     */
    void injectJavascriptFile();

    /**
     * 处理JS回调消息
     */
    void flushMessageQueue(String messageQueue);

    /**
     * 调度消息
     * @param msg
     */
    void dispatchMessage(IBridgeMessage msg);
}
