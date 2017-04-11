package com.apple.uichoose.common;


import android.content.Context;

/**
 * 定义路由配置类
 * @author hushaoping
 */
public  class ChooseConf {

    private final ConfFactory confFactory;
    private final Context context;



    private ChooseConf(Builder builder) {
        this.confFactory=builder.confFactory;
        this.context=builder.mContext;
    }


    public Context getContext() {
        return context;
    }

    public ConfFactory getConfFactory() {
        return confFactory;
    }



    /**
     * Builder for {@link ChooseConf}
     * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
     */
    public static class Builder {
        private ConfFactory confFactory;
        private Context mContext;
        public Builder() {
        }

        /**
         * 路由配置
         * @param confFactory
         * @return
         */
        public Builder setConfFactory(ConfFactory confFactory) {
            this.confFactory = confFactory;
            return this;
        }

        public Builder setmContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        /** Builds configured {@link ChooseConf} object */
        public ChooseConf build() {
            return new ChooseConf(this);
        }
    }


}
