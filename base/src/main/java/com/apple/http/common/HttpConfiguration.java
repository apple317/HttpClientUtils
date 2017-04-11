package com.apple.http.common;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.apple.http.cookie.okhttp.CookieJarImpl;

import java.io.File;
import java.net.Proxy;
import java.net.ProxySelector;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionPool;


/**
 * 定义一个http网络访问配置类
 * @author hushaoping
 */
public class HttpConfiguration {


    private final int connectTimeout;
    private final int readTimeout;
    private final int writeTimeout;
    private final Proxy proxy;
    private final boolean retryOnConnectionFailure;
    private final long diskCacheSize;
    private final File cacheDir;
    public Context mContext;
    private final CookieJarImpl cookieJar;
    private final Handler handler;
    private final String baseUrl;

    private final SSLSocketFactory certificates;



    private HttpConfiguration(Builder builder) {
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        proxy=builder.proxy;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        diskCacheSize=builder.diskCacheSize;
        cacheDir=builder.cacheDir;
        cookieJar=builder.cookieJar;
        this.mContext=builder.context;
        this.certificates=builder.certificates;
        this.handler = builder.handler;
        this.baseUrl=builder.baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public SSLSocketFactory getCertificates() {
        return certificates;
    }

    public CookieJarImpl getCookieJar() {
        return cookieJar;
    }

    public Context getContext() {
        return mContext;
    }

    public Handler getHandler() {
        return this.handler;
    }

    /**
     *得到网络连接是否重试
     * @return
     */
    public boolean isRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }


    /**
     * 得到缓冲大小
     * @return
     */
    public long getDiskCacheSize() {
        return diskCacheSize;
    }

    /**
     * 得到缓冲目录路径
     * @return
     */
    public File getCacheDir() {
        return cacheDir;
    }

    /**
     * 返回连接超时设置
     * @return
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 返回写入超时设置
     * @return
     */
    public int getWriteTimeout() {
        return writeTimeout;
    }

    /**
     * 返回读操作超时设置
     * @return
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    public boolean retryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }
    /**
     * Builder for {@link HttpConfiguration}
     *
     * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
     */
    public static class Builder {

        int connectTimeout=10000;
        int readTimeout=10000;
        int writeTimeout=10000;
        private Context context;
        private  Proxy proxy;
        boolean retryOnConnectionFailure;
        private boolean writeLogs = false;
        private long diskCacheSize = 0;
        private  File cacheDir;
        private  CookieJarImpl cookieJar;
        private  SSLSocketFactory certificates;
        private Handler handler;

        private  String baseUrl;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
            this.handler = defineHandler();
        }

        public Builder handler(Handler handler) {
            this.handler = handler;
            return this;
        }


        private  Handler defineHandler() {
            if(handler == null && Looper.myLooper() == Looper.getMainLooper()) {
                handler = new Handler();
            }
            return handler;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(long timeout) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (timeout > Integer.MAX_VALUE) throw new IllegalArgumentException("Timeout too large.");
            this.connectTimeout = (int) timeout;
            return this;
        }


        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置证书
         */
        public Builder setCertificates(SSLSocketFactory certificates) {
            this.certificates=certificates;
            return this;
        }

        /**
         * 设置持久化设置
         */
        public Builder setCookieJar(CookieJarImpl cookieJar) {
            this.cookieJar=cookieJar;
            return this;
        }


        /**
         * Sets the default read timeout for new connections. A value of 0 means no timeout, otherwise
         * values must be between 1 and {@link Integer#MAX_VALUE} when converted to milliseconds.
         */
        public Builder readTimeout(long timeout) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (timeout > Integer.MAX_VALUE) throw new IllegalArgumentException("Timeout too large.");
            readTimeout = (int) timeout;
            return this;
        }

        /**
         * Sets the default write timeout for new connections. A value of 0 means no timeout, otherwise
         * values must be between 1 and {@link Integer#MAX_VALUE} when converted to milliseconds.
         */
        public Builder writeTimeout(long timeout) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (timeout > Integer.MAX_VALUE) throw new IllegalArgumentException("Timeout too large.");
            writeTimeout = (int) timeout;
            return this;
        }
        /**
         * Enables detail logging of {@link BaseHttpClient} work. To prevent detail logs don't call this method.
         * ImageLoader logging completely (even error logs)
         */
        public Builder writeDebugLogs() {
            this.writeLogs = true;
            return this;
        }

        /**
         * Sets the HTTP proxy that will be used by connections created by this client. This takes
         * precedence over {@link }, which is only honored when this proxy is null (which
         * it is by default). To disable proxy use completely, call {@code setProxy(Proxy.NO_PROXY)}.
         */
        public Builder proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        /** Builds configured {@link HttpConfiguration} object */
        public HttpConfiguration build() {
            return new HttpConfiguration(this);
        }
        /**
         * Configure this client to retry or not when a connectivity problem is encountered. By default,
         * this client silently recovers from the following problems:
         *
         * <ul>
         *   <li><strong>Unreachable IP addresses.</strong> If the URL's host has multiple IP addresses,
         *       failure to reach any individual IP address doesn't fail the overall request. This can
         *       increase availability of multi-homed services.
         *   <li><strong>Stale pooled connections.</strong> The {@link ConnectionPool} reuses sockets
         *       to decrease request latency, but these connections will occasionally time out.
         *   <li><strong>Unreachable proxy servers.</strong> A {@link ProxySelector} can be used to
         *       attempt multiple proxy servers in sequence, eventually falling back to a direct
         *       connection.
         * </ul>
         *
         * Set this to false to avoid retrying requests when doing so is destructive. In this case the
         * calling application should do its own recovery of connectivity failures.
         */
        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        /** @deprecated Use {@link #diskCacheSize(int)} instead */
        @Deprecated
        public Builder discCacheSize(int maxCacheSize) {
            return diskCacheSize(maxCacheSize);
        }

        /**
         * Sets maximum disk cache size for http (in bytes).<br />
         * By default: disk cache is unlimited.<br />
         * <b>NOTE:</b> If you use this method then
         * {@link }
         * will be used as disk cache. You can use {@link #)} method for introduction your own
         * implementation of {@link }
         */
        public Builder diskCacheSize(int maxCacheSize) {
            if (maxCacheSize <= 0) throw new IllegalArgumentException("maxCacheSize must be a positive number");
            this.diskCacheSize = maxCacheSize;
            return this;
        }
        /**
         * Sets cache directory  for file <br />
         * By default: cache directory is unlimited.<br />
         */
        public Builder diskCacheDir(File cacheDir) {
            if (cacheDir ==null) throw new IllegalArgumentException("cacheDir==null");
            if (!cacheDir.isDirectory()) throw new IllegalArgumentException("cache directory must be directory");
            this.cacheDir = cacheDir;
            return this;
        }



    }
}
