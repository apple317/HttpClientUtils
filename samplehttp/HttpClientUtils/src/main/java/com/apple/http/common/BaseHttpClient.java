package com.apple.http.common;


import com.apple.http.impl.OkHttpImpl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;

import android.content.Context;


/**
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first interface to define the request,
 * and can be customized to achieve a new interface
 *
 * @author 胡少平
 */
public class BaseHttpClient {

    //单例模式实现
    Context mContext;
    static BaseHttpClient baseClient = null;


    public static BaseHttpClient getBaseClient(Context context) {
        if (baseClient == null)
            baseClient = new BaseHttpClient(context);
        return baseClient;
    }

    private BaseHttpClient(Context context) {
        mContext = context;
    }

    /**
     * 传入tag以便于关闭线程
     * @param tag
     * @param url
     * @param params
     * @param callback
     * @return
     */
    public Call sendGetRequest(Object tag,String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient(mContext).get(tag,false,url, params, callback,null,null);
    }

    public Call sendGetRequest(String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient(mContext).get(url, params, callback);
    }

    public Call sendPostRequest(Object tag,String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        return  OkHttpImpl.getOkClient(mContext).post(tag, url, params, callback, null, null);
    }

    public Call sendPostRequest(String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
       return  OkHttpImpl.getOkClient(mContext).post(url, params, callback);
    }

    public Call sendGetRequest(Boolean shouldEncodeUrl, String url, BaseParams params, HttpCallback callback, Headers headers) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient(mContext).get(shouldEncodeUrl, url, params, callback, headers, null);
    }

    /**
     * 返回okhttp网络请求对象
     */
    public OkHttpClient getOkHttpClient() {
        return OkHttpImpl.getOkClient(mContext).mOkHttpClient;
    }



    /**
     * 通过tag可以来取消网络请求
     * @param  object
     *
     */
    public void cancelTag(Object object) {
        try {
//            if(OkHttpClient==null)
//                getOkClient(mContext);
//            if(okImpl.mOkHttpClient==null)
//                OkHttpImpl.getOkClient(mContext);
            getOkHttpClient().cancel(object);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
