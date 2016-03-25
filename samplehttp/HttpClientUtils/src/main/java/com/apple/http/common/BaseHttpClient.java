package com.apple.http.common;


import com.apple.http.impl.OkHttpImpl;
import com.squareup.okhttp.Headers;

import android.content.Context;

/**
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first interface to define the request,
 * and can be customized to achieve a new interface
 *
 * @author 胡少平
 */
public class BaseHttpClient {

    OkHttpImpl okImpl = null;
    //单例模式实现
    private static BaseHttpClient instance;
    public static BaseHttpClient getOkClient(Context context) {
        if (instance == null)
            instance = new BaseHttpClient(context);
        return instance;
    }

    private BaseHttpClient(Context context) {
        okImpl=OkHttpImpl.getOkClient(context);
    }

    public void sendGetRequest(String url, BaseParams params,HttpCallback callback) {
        // TODO Auto-generated method stub
        if (okImpl != null) {
            okImpl.get(url, params, callback);
        }
    }

    public void sendPostRequest(String url, BaseParams params,HttpCallback callback){
        // TODO Auto-generated method stub
        if (okImpl != null) {
            okImpl.post(url, params, callback);
        }
    }

    public void sendGetRequest(Boolean shouldEncodeUrl,String url, BaseParams params,HttpCallback callback,Headers headers) {
        // TODO Auto-generated method stub
        if (okImpl != null) {
            okImpl.get(shouldEncodeUrl,url, params, callback,headers,null);
        }
    }
}
