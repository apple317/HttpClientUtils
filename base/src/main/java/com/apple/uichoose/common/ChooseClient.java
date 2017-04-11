package com.apple.uichoose.common;


import android.app.Activity;

import com.apple.uichoose.impl.ChooseImpl;
import com.apple.uichoose.listener.ChooseListener;

import java.util.HashMap;

/**
 * @author 胡少平
 */
public class ChooseClient {

    //单例模式实现
    static ChooseClient baseClient = null;

    public static ChooseConf chooseConf;

    private HashMap<String, Object> paramData;

    private String url;
    private int requestCode = -1;

    public static ChooseClient getBaseClient() {
        if (baseClient == null) {
            synchronized (ChooseClient.class) {
                if (baseClient == null) {
                    baseClient = new ChooseClient();
                }
            }
        }
        return baseClient;
    }

    public ChooseClient() {
    }

    private ChooseClient(Builder builder) {
        this.url = builder.url;
        this.requestCode = builder.requestCode;
        this.paramData=builder.paramData;
    }


    public HashMap<String, Object> getParamData() {
        return paramData;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public String getUrl() {
        return url;
    }

    /**
     * 路由url配置
     */
    public synchronized void init(ChooseConf chooseConf) {
        if (chooseConf == null) {
            throw new IllegalArgumentException("");
        }
        this.chooseConf = chooseConf;
    }


    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * 路由控制器执行
     *
     * @BaseFilter 过滤器
     */
    public void execute(BaseFilter baseFilter) {
        ChooseImpl.getChooseImpl().openUrl(baseFilter, chooseConf, baseClient);
    }


    public static final class Builder {

        String url;
        int requestCode = -1;

        HashMap<String, Object> paramData;

        public Builder() {
            url = "";
        }


        /**
         * 设置路由传递数值
         *
         * @param parameter
         */
        public Builder setParameter(HashMap<String, Object> parameter) {
            this.paramData = parameter;
            return this;
        }


        /**
         * @param url
         * @return
         */
        public Builder url(String url) {
            if (url == null) throw new IllegalArgumentException("url ==null");
            if (url.trim().equals(""))
                throw new IllegalArgumentException("Url is empty.");
            this.url = url;
            return this;
        }


        /**
         * @param requestCode
         * @return
         */
        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }


        public ChooseClient build() {
            return new ChooseClient(this);
        }


    }


}
