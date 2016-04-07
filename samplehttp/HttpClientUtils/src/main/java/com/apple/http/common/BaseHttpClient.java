package com.apple.http.common;


import com.apple.http.entity.METHOD;
import com.apple.http.impl.BaseHttpImpl;
import com.apple.http.listener.BaseCallback;
import com.apple.http.impl.okhttp.OkHttpImpl;
import com.apple.http.utils.L;
import com.apple.http.utils.MD5Util;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first interface to define the request,
 * and can be customized to achieve a new interface
 *
 * @author 胡少平
 */
public class BaseHttpClient {

    public static final String TAG = BaseHttpClient.class.getSimpleName();
    //单例模式实现
    static BaseHttpClient baseClient = null;
    private  String method;
    private  BaseParams mParams;
    private  String destFileName;
    private  String destFileDir;
    private  String url;
    private  Object tag;
    private  boolean shouldEncodeUrl;
    private String urlIdentifier;

    //解析类
    private Class parse;
    private String content;
    public static  HttpConfiguration configuration;
    private static final String WARNING_RE_INIT_CONFIG = "Try to initialize BaseHttpClient which had already been initialized before. " + "To re-init BaseHttpClient with new configuration call BaseHttpClient.destroy() at first.";
    private static final String ERROR_INIT_CONFIG_WITH_NULL = "BaseHttpClient configuration can not be initialized with null";
    static final String LOG_INIT_CONFIG = "Initialize BaseHttpClient with configuration";

    public static BaseHttpClient getBaseClient() {
        if (baseClient == null) {
            synchronized (BaseHttpClient.class) {
                if (baseClient == null) {
                    baseClient = new BaseHttpClient();
                }
            }
        }
        return baseClient;
    }

    public BaseHttpClient() {
    }

    private BaseHttpClient(Builder builder) {
        this.tag=builder.tag;
        this.url=builder.url;
        this.destFileDir=builder.destFileDir;
        this.destFileName=builder.destFileName;
        this.mParams=builder.mParams;
        this.method=builder.method;
        this.shouldEncodeUrl=builder.shouldEncodeUrl;
        this.parse=builder.parse;
        this.urlIdentifier=builder.urlIdentifier;
    }


    public static HttpConfiguration getConfiguration() {
        return configuration;
    }

    public String getMethod() {
        return method;
    }

    public BaseParams getParams() {
        return mParams;
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public String getUrl() {
        return url;
    }

    public Object getTag() {
        return tag;
    }


    public String getUrlIdentifier() {
        return urlIdentifier;
    }

    public Class getParse() {
        return parse;
    }

    public boolean isShouldEncodeUrl() {
        return shouldEncodeUrl;
    }


    public String getContent() {
        return content;
    }

    /**
     * Initializes ImageLoader instance with configuration.<br />
     * If configurations was set before ( {@link #isInited()} == true) then this method does nothing.<br />
     *
     * @param configuration {@linkplain HttpConfiguration BaseHttpClient configuration}
     * @throws IllegalArgumentException if <b>configuration</b> parameter is null
     */
    public synchronized void init(HttpConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (this.configuration == null) {
            L.d(LOG_INIT_CONFIG);
            this.configuration = configuration;
        } else {
            L.w(WARNING_RE_INIT_CONFIG);
        }
        getHttpImpl().init(configuration);
    }



    /**
     * Returns <b>true</b> - if BaseHttpClient {@linkplain #init(HttpConfiguration) is initialized with
     * configuration}; <b>false</b> - otherwise
     */
    public boolean isInited() {
        return configuration != null;
    }



    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     *  关闭网络库可以通过传入url来关闭
     * @param url
     */
    public void cancel(String url) {
        try {
            for (okhttp3.Call call :  OkHttpImpl.getOkClient().mOkHttpClient.dispatcher().queuedCalls())
            {
                if (MD5Util.md5(url).equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
            for (okhttp3.Call call :  OkHttpImpl.getOkClient().mOkHttpClient.dispatcher().runningCalls())
            {
                if (MD5Util.md5(url).equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
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
            for (okhttp3.Call call :  OkHttpImpl.getOkClient().mOkHttpClient.dispatcher().queuedCalls())
            {
                if (object.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
            for (okhttp3.Call call :  OkHttpImpl.getOkClient().mOkHttpClient.dispatcher().runningCalls())
            {
                if (object.equals(call.request().tag()))
                {
                    call.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *直接执行不去
     */
    public void execute(){
        execute(null);
    }

    /**
     * 传入请求返回监听
     * <p>This client will later call back {@code responseCallback} with either an HTTP response or a
     * failure exception.
     *
     */
    public void execute(BaseCallback responseCallback){
        getHttpImpl().execute(this,responseCallback);
    }


    /**
     * 得到当前网络扩展类
     * 得到okhttp扩展类
     * 切换网络库记住此处切换
     * @return
     */
    private BaseHttpImpl getHttpImpl(){
       return  OkHttpImpl.getOkClient();
    }


    public static final class Builder {

        Object tag;
        String url;
        String destFileDir;
        String destFileName;
        BaseParams mParams;
        String method;
        Class parse;
        String urlIdentifier;
        boolean shouldEncodeUrl;
        //文本post上传字符串
        String content;
        public Builder() {
            tag=null;
            url="";
            destFileDir="";
            destFileName="";
            if (mParams == null) {
                mParams = new BaseParams();
            } else {
                mParams.clear();
            }
            method= METHOD.GET;
            shouldEncodeUrl=false;
            content="";
            urlIdentifier="";
        }


        public Builder setParse(Class parse) {
            this.parse = parse;
            return this;
        }

        /**
         * 添加网络请求地址
         Add network request address
         * @param url
         * @return
         */
        public Builder url(String url) {
            if (url ==null) throw new IllegalArgumentException("url ==null");
            if (url.trim().equals(""))
                throw new IllegalArgumentException("Url is empty.");
            this.url = url;
            return this;
        }
        /**
         *设置上传文本内容
         Add network request address
         * @param content
         * @return
         */
        public Builder content(String content) {
            if (content ==null) throw new IllegalArgumentException("url ==null");
            if (content.trim().equals(""))
                throw new IllegalArgumentException("Url is empty.");
            this.content = content;
            return this;
        }


        /**
         * 设置url标识
         * @param urlIdentifier
         * @return
         */
        public Builder urlIdentifier(String urlIdentifier) {
            this.urlIdentifier = urlIdentifier;
            return this;
        }

        /**
         * 设置url地址编码格式化
         * true 编码utf_8
         * @return
         */
        public Builder shouldEncodeUrl(boolean isEncode){
            this.shouldEncodeUrl = isEncode;
            return this;
        }

        /**
         * 设置网络请求方式
         Add network request address
         * @param method
         * @return
         */
        public Builder method(String method) {
            if (method ==null) throw new IllegalArgumentException("method ==null");
            if (method.trim().equals(""))
                throw new IllegalArgumentException("method is empty.");
            this.method = method;
            return this;
        }



        /**
         * Adds a long value to the request.
         *
         * @param key   the key name for the new param.
         * @param value the value long for the new param.
         */
        public Builder put(String key, long value) {
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
        public Builder put(String key, ArrayList<File> file) throws FileNotFoundException {
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
        public Builder put(String key, File file) throws FileNotFoundException {
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
        public Builder put(String key, String customFileName, File file) throws FileNotFoundException {
            mParams.put(key, file, null, customFileName);
            return this;
        }




        /**
         * Adds a key/value string pair to the request.
         *
         * @param key   the key name for the new param.
         * @param value the value string for the new param.
         */
        public Builder put(String key, String value) {
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
        public Builder put(String key, int value) {
            if (key != null) {
                mParams.put(key, String.valueOf(value));
            }
            return this;
        }


        /**
         * 设置参数
         */
        public Builder setBaseParams(BaseParams baseParams) {
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
         * 设置下载文件目录
         * @param  dir
         */

        public Builder downDir(String dir) {
            this.destFileDir=dir;
            return this;
        }

        /**
         * 设置下载文件名称
         * @param fileName
         */
        public Builder downName(String fileName) {
            this.destFileName=fileName;
            return this;
        }


        /**
         * 设置网络关闭请求tag
         * @param tag
         */
        public Builder setTag(Object tag) {
            if (tag ==null) throw new IllegalArgumentException("tag ==null");
            this.tag=tag;
            return this;
        }

        public BaseHttpClient build() {
            return new BaseHttpClient(this);
        }

    }



}
