package com.apple.web;

/**
＊
 */
public interface IBridgeMessage {

    /**
     * 回调id
     * @return
     */
    String callbackId();

    /**
     * 响应id
     * @return
     */
    String responseId();

    /**
     * 处理名称
     * @return
     */
    String handlerName();

    /**
     * 获取数据
     * @return
     */
    Object data();

    /**
     * 是否为回调
     * @return
     */
    boolean isCallback();

    /**
     * 是否为响应
     * @return
     */
    boolean isResponse();

    /**
     * 响应数据
     * @return
     */
    Object responseData();
}
