package com.apple.http.common;


import com.apple.http.impl.OkHttpImpl;
import com.apple.http.utils.MD5Util;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


/**
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first interface to define the request,
 * and can be customized to achieve a new interface
 *
 * @author 胡少平
 */
public class BaseHttpClient {

    //单例模式实现
    static BaseHttpClient baseClient = null;

    BaseParams mParams;

    /**
     * 网络访问地址
     */
    String url = "";

    public static BaseHttpClient getBaseClient() {
        if (baseClient == null)
            baseClient = new BaseHttpClient();
        return baseClient;
    }

    private BaseHttpClient() {
        url = "";
        initParameter();
    }

    /**
     * 设置参数
     */
    public BaseHttpClient setBaseParams(BaseParams baseParams) {
        if (baseParams != null) {
            initParameter();
            mParams = baseParams;
        }
        return this;
    }


    /**
     * 初始化参数
     * 此方法也可以用来项目公共参数定义传入。
     */
    protected void initParameter() {
        if (mParams == null) {
            mParams = new BaseParams();
        } else {
            mParams.clear();
        }
    }


    /**
     * 添加网络请求地址
     * @param requestUrl
     * @return
     */
    public BaseHttpClient addUrl(String requestUrl) {
        if (url != null) {
            url = requestUrl;
        }
        return this;
    }

    /**
     * Adds a long value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value long for the new param.
     */
    public BaseHttpClient put(String key, long value) {
        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
        return this;
    }




    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public BaseHttpClient put(String key, File[] file) throws FileNotFoundException {
        if (key != null && file != null) {
            mParams.put(key, file, null, null);
        }
        return this;
    }


    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public BaseHttpClient put(String key, File file) throws FileNotFoundException {
        if (key != null && file != null) {
            mParams.put(key, file, null, null);
        }
        return this;
    }

    /**
     * Adds a file to the request with custom provided file name
     *
     * @param key            the key name for the new param.
     * @param file           the file to add.
     * @param customFileName file name to use instead of real file name
     * @throws FileNotFoundException throws if wrong File argument was passed
     */
    public BaseHttpClient put(String key, String customFileName, File file) throws FileNotFoundException {
        mParams.put(key, file, null, customFileName);
        return this;
    }




    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    public BaseHttpClient put(String key, String value) {
        if (key != null && value != null) {
            mParams.put(key, value);
        }
        return this;
    }


    /**
     * Adds a int value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value int for the new param.
     */
    public BaseHttpClient put(String key, int value) {
        if (key != null) {
            mParams.put(key, String.valueOf(value));
        }
        return this;
    }


    /**
     *
     * @param callback
     * @return
     */
    public BaseHttpClient getRequest(HttpCallback callback) {
        OkHttpImpl.getOkClient().setTag(MD5Util.md5(url));
        OkHttpImpl.getOkClient().get(url, mParams, callback);
        return this;
    }

    /**
     *
     * @param callback
     * @return
     */
    public BaseHttpClient postRequest(HttpCallback callback) {
        OkHttpImpl.getOkClient().setTag(MD5Util.md5(url));
        OkHttpImpl.getOkClient().post(url, mParams, callback);
        return this;
    }




    /**
     * 设置网络链接超时操作
     */

    public BaseHttpClient setConnectTimeout(int timeout, TimeUnit units) {
        OkHttpImpl.getOkClient().setConnectTimeout(timeout, units);
        return this;
    }

    /**
     * 设置网络读超时操作
     */
    public BaseHttpClient setReadTimeout(int timeout, TimeUnit units) {
        OkHttpImpl.getOkClient().setReadTimeout(timeout, units);
        return this;
    }


    /**
     * 设置tag操作
     */
    public BaseHttpClient setTag(Object tag) {
        OkHttpImpl.getOkClient().setTag(tag);
        return this;
    }

    /**
     * 设置网络写超时设置
     */
    public BaseHttpClient setWriteTimeout(int timeout, TimeUnit units) {
        OkHttpImpl.getOkClient().setWriteTimeout(timeout, units);
        return this;
    }

    /**
     * 传入tag以便于关闭线程
     */
    public Call sendGetRequest(String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient().get(false, url, params, callback, null, null);
    }


    public Call sendPostRequest(String url, BaseParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient().post(url, params, callback, null, null);
    }


    public Call sendGetRequest(Boolean shouldEncodeUrl, String url, BaseParams params, HttpCallback callback, Headers headers) {
        // TODO Auto-generated method stub
        return OkHttpImpl.getOkClient().get(shouldEncodeUrl, url, params, callback, headers, null);
    }


    /**
     * 返回okhttp网络请求对象
     */
    public OkHttpClient getOkHttpClient(OkHttpClient client) {
        return OkHttpImpl.getOkClient(client).mOkHttpClient;
    }


    /**
     *  关闭网络库可以通过传入url来关闭
     * @param url
     */
    public void cancel(String url) {
        try {
            OkHttpImpl.getOkClient().mOkHttpClient.cancel(MD5Util.md5(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过tag可以来取消网络请求
     * @param object
     */
    public void cancelTag(Object object) {
        try {
            OkHttpImpl.getOkClient().mOkHttpClient.cancel(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
