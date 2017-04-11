package com.apple.h5method;

import android.app.Dialog;
import android.content.Context;
import android.webkit.WebSettings;

import com.apple.web.BridgeWebView;
import com.apple.web.IRegisterHandler;
import com.apple.web.IResponseCallback;



/**
 *web JsBridge基础方法
 * 继承扩张基础类
 */
public class BaseJsInteractionMethod {
    public Dialog dialog;
    public BridgeWebView webView;
    public Context context;

    public BaseJsInteractionMethod(BridgeWebView webView, Context mContext) {
        this.context = mContext;
        WebSettings settings = webView.getSettings();
        //适应屏幕
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        //不使用缓存：
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
    }


    /**
     * web bride js 注册监听
     * @param handlerName
     * WebResponseCallback web监听返回方法
     */
    public void registerHandler(String handlerName,final  WebResponseCallback webResponseCallback){
        webView.registerHandler(handlerName, new IRegisterHandler() {
            @Override
            public void handler(Object data, IResponseCallback responseCallback) {
                responseCallback.responseCallback(webResponseCallback.responseCallback(data));
            }
        });
    }



    /**
     * web bride js 注册监听
     * html 调用native
     * @param handlerName
     * WebResponseCallback web监听返回方法
     */
    public void callHandler(String handlerName,final  WebResponseCallback webResponseCallback){
        webView.callHandler(handlerName, new IResponseCallback() {
            @Override
            public void responseCallback(Object responseData) {
                webResponseCallback.responseCallback(responseData);
            }
        });
    }







}
