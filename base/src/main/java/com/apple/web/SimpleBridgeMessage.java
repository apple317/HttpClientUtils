package com.apple.web;

/**
 *
 */
public class SimpleBridgeMessage implements IBridgeMessage {

    /**
     * 回调id
     */
    private String callbackId;

    /**
     * 响应id
     */
    private String responseId;

    /**
     * 函数名称
     */
    private String handlerName;

    /**
     * 传输参数数据
     */
    private Object data;

    /**
     * 响应数据
     */
    private Object responseData;

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public String callbackId() {
        return this.callbackId;
    }

    @Override
    public String responseId() {
        return this.responseId;
    }

    @Override
    public Object data() {
        return this.data;
    }

    @Override
    public boolean isCallback() {
        if (null != callbackId) return true;
        return false;
    }

    @Override
    public String handlerName() {
        return this.handlerName;
    }

    @Override
    public boolean isResponse() {
        if (null != responseId) return true;
        return false;
    }

    @Override
    public Object responseData() {
        return this.responseData;
    }
}
