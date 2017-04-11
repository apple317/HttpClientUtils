package com.apple.web;

/**
 * Created by halayun on 16/6/25.
 */
public interface IRegisterHandler {

    /**
     * 处理消息
     * @param data js传过来的参数
     * @param responseCallback 响应回调
     */
    void handler(Object data, IResponseCallback responseCallback);

}
