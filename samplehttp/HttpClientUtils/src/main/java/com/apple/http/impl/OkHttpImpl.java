package com.apple.http.impl;


import com.apple.http.common.BaseHttpImpl;
import com.apple.http.common.BaseOkHandler;
import com.apple.http.common.BaseParams;
import com.apple.http.common.HttpCallback;
import com.apple.http.utils.StorageUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


import com.squareup.okhttp.Request;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 *
 * OkHttpImpl such is the package okhttp network library independent implementation module,
 * in such a network data request.
 *
 * @author 胡少平
 */


public class OkHttpImpl implements BaseHttpImpl {

    OkHttpClient mOkHttpClient = null;
    //单例模式实现
    private static OkHttpImpl instance;
    private final String HTTP_CACHE_FILENAME = "HttpCache";


    public static OkHttpImpl getOkClient(Context context) {
        if (instance == null)
            instance = new OkHttpImpl(context);
        return instance;
    }

    private OkHttpImpl(Context context) {
        Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", String.format("max-age=%d", 60))
                        .build();
            }
        };
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(15000, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(15000, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(15000, TimeUnit.SECONDS);
        mOkHttpClient.setRetryOnConnectionFailure(true);
        //-------------------------------设置http缓存，提升用户体验-----------------------------------
        Cache cache;
        File httpCacheDirectory =  StorageUtils.getOwnCacheDirectory(context,HTTP_CACHE_FILENAME);
        cache = new Cache(httpCacheDirectory, 10 * 1024);
        mOkHttpClient.setCache(cache);
        mOkHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        //-------------------------------设置http缓存，提升用户体验-----------------------------------

       // Handler mDelivery = new Handler(Looper.getMainLooper());

        if (false) {
            mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }
        /**
         *  final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
        .removeHeader("Pragma")
        .header("Cache-Control", String.format("max-age=%d", 60))
        .build();
        }
        };
         client = new OkHttpClient();
         //       client.newBuilder().connectTimeout(15000, TimeUnit.SECONDS);
         //        client.newBuilder().readTimeout(15000, TimeUnit.SECONDS);
         //        client.newBuilder().writeTimeout(15000, TimeUnit.SECONDS);
         //-------------------------------设置http缓存，提升用户体验-----------------------------------
         Cache cache;
         File httpCacheDirectory = StorageUtils.getOwnCacheDirectory(context,HTTP_CACHE_FILENAME);
         cache = new Cache(httpCacheDirectory, 10 * 1024);
         client.newBuilder().cache(cache);
         client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
         //-------------------------------设置http缓存，提升用户体验-----------------------------------

         Handler mDelivery = new Handler(Looper.getMainLooper());
         if (false) {
         client.newBuilder().hostnameVerifier(new HostnameVerifier(){
        @Override public boolean verify(String hostname, SSLSession session) {
        return true;
        }
        });
         }
         */

    }
    @Override
    public void get(String url, BaseParams params, HttpCallback callback) {
        get(false,url, params, callback, null, null);
    }



    @Override
    public void post(String url, BaseParams params, HttpCallback callback) {
        post(url, params, callback, null, null);
    }

    @Override
    public void post(String url, BaseParams params, HttpCallback callback, Object head, Object config) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (ConcurrentHashMap.Entry<String, String> entry : params.urlParams.entrySet()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                    RequestBody.create(null, entry.getValue()));
        }
        if (params.fileParams.size() > 0) {
            RequestBody fileBody = null;
            for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : params.fileParams.entrySet()) {
                {
                    File file = entry1.getValue().file;
                    String fileName = file.getName();
                    fileBody = RequestBody.create(MediaType.parse(URLEncodedUtils.guessMimeType(fileName)), file);
                    //TODO 根据文件名设置contentType
                    builder.addPart(Headers.of("Content-Disposition",
                                    "form-data; name=\"" + entry1.getKey() + "\"; filename=\"" + fileName + "\""),
                            fileBody);
                }
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        try {
            BaseOkHandler handler = new BaseOkHandler(callback, url, null);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void get(boolean shouldEncodeUrl,String url, BaseParams params, HttpCallback callback, Object head, Object config) {
        FormEncodingBuilder body = new FormEncodingBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : params.urlParams.entrySet()) {
            body.addEncoded(entry.getKey(), entry.getValue());
        }
        try {
            Request request1 = new Request.Builder()
                    .url(URLEncodedUtils.getUrlWithQueryString(shouldEncodeUrl, url, params))
                    .build();
            BaseOkHandler handler = new BaseOkHandler(callback, url, null);
            mOkHttpClient.newCall(request1).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
