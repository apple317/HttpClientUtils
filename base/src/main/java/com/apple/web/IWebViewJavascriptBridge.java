package com.apple.web;

/**
 * WebView桥接接口
 */
public interface IWebViewJavascriptBridge {


    /**
     * 接收JS调用
     * @param handlerName 函数名称
     * @param handler 处理器
     */
    void registerHandler(String handlerName, IRegisterHandler handler);

    /**
     * 调用JS
     * @param handlerName 函数名称
     */
    void callHandler(String handlerName);

    /**
     * 调用JS
     * @param handlerName 函数名称
     * @param callback 响应结果
     */
    void callHandler(String handlerName, IResponseCallback callback);

    /**
     * 调用JS
     * @param handlerName 函数名称
     * @param data 发送数据
     * @param callback 响应结果
     */
    void callHandler(String handlerName, Object data, IResponseCallback callback);



}
