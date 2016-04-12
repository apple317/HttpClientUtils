package com.apple.http.impl.okhttp;


import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.HttpConfiguration;
import com.apple.http.entity.METHOD;
import com.apple.http.impl.BaseHttpImpl;
import com.apple.http.listener.BaseCallback;
import com.apple.http.utils.URLEncodedUtils;
import com.apple.http.listener.okhttp.BaseOkCall;
import com.apple.http.listener.okhttp.DownFileCall;
import com.apple.http.common.BaseParams;
import com.apple.http.listener.okhttp.ProgressRequestBody;
import com.apple.http.utils.MD5Util;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;


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

    //单例模式实现
    public OkHttpClient mOkHttpClient;

    public static OkHttpImpl instance;

    public static OkHttpImpl getOkClient(OkHttpClient okHttpClient) {
        if (instance == null) {
            synchronized (OkHttpImpl.class) {
                if (instance == null) {
                    instance = new OkHttpImpl(okHttpClient);
                }
            }
        } else {
            if (okHttpClient != null) {
                instance.mOkHttpClient = okHttpClient;
            }
        }
        return instance;
    }

    @Override
    public void init(HttpConfiguration configuration) {
        mOkHttpClient.newBuilder().connectTimeout(configuration.getConnectTimeout(), TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().readTimeout(configuration.getReadTimeout(), TimeUnit.SECONDS);
        mOkHttpClient.newBuilder().writeTimeout(configuration.getWriteTimeout(), TimeUnit.SECONDS);
        if(configuration.getCacheDir()!=null&&configuration.getCacheDir().isDirectory()){
            Cache cache = new Cache(configuration.getCacheDir(), configuration.getDiskCacheSize());
            mOkHttpClient.newBuilder().cache(cache);
        }
        if(configuration.getCookieJar()!=null)
            mOkHttpClient.newBuilder().cookieJar(configuration.getCookieJar());

        if(configuration.getCertificates()!=null)
            mOkHttpClient.newBuilder().sslSocketFactory(configuration.getCertificates());
        mOkHttpClient.newBuilder().retryOnConnectionFailure(configuration.isRetryOnConnectionFailure());
    }

    public static OkHttpImpl getOkClient() {
        if (instance == null) {
            synchronized (OkHttpImpl.class) {
                if (instance == null) {
                    instance = new OkHttpImpl(null);
                }
            }
        }
        return instance;
    }

    public OkHttpImpl(OkHttpClient okHttpClient) {
        if (okHttpClient != null) {
            mOkHttpClient = okHttpClient;
        } else {
            if (mOkHttpClient == null)
                mOkHttpClient = new OkHttpClient();
        }
    }



    @Override
    public void execute(BaseHttpClient client,BaseCallback responseCallback){
        switch (client.getMethod()){
            case METHOD.GET:
                get(client,responseCallback);
                break;
            case METHOD.POST_STRING:
                postString(client, responseCallback);
                break;
            case METHOD.POST_FORM:
                postForm(client, responseCallback);
                break;
            case METHOD.POST_FILE:
                postFile(client, responseCallback);
                break;
            case METHOD.DOWNLOAD_FILE:
                downloadFile(client,responseCallback);
                break;
            case METHOD.POST_FILE_PROGRESS:
                postFileProgress(client, responseCallback);
                break;
            case METHOD.POST_FORM_PROGRESS:
                postFormProgress(client, responseCallback);
                break;
            case METHOD.PUT:
                put(client, responseCallback);
                break;
            case METHOD.PATCH:
                patch(client, responseCallback);
                break;
            case METHOD.DELETE:
                delete(client, responseCallback);
                break;
        }
    }

    @Override
    public void get(BaseHttpClient client, BaseCallback callback) {
        Call call = null;
        try {
            Request request;
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams())).tag(client.getTag())
                        .get().build();
            } else {
                request = new Request.Builder().tag(MD5Util.md5(client.getUrl()))
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams()))
                        .get().build();
            }
            BaseOkCall handler = new BaseOkCall(callback,client);
            call = mOkHttpClient.newCall(request);
            call.enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(BaseHttpClient client, BaseCallback callback) {
        Call call = null;
        try {
            Request request;
            RequestBody requestBody = new RequestBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            };
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(client.getUrl()).tag(client.getTag())
                        .put(requestBody).build();
            } else {
                request = new Request.Builder().tag(MD5Util.md5(client.getUrl()))
                        .url(client.getUrl())
                        .put(requestBody).build();
            }
            BaseOkCall handler = new BaseOkCall(callback, client);
            call = mOkHttpClient.newCall(request);
            call.enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void patch(BaseHttpClient client, BaseCallback callback) {
        Call call = null;
        try {
            Request request;
            RequestBody requestBody = new RequestBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            };
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(client.getUrl()).tag(client.getTag())
                        .patch(requestBody).build();
            } else {
                request = new Request.Builder().tag(MD5Util.md5(client.getUrl()))
                        .url(client.getUrl())
                        .patch(requestBody).build();
            }
            BaseOkCall handler = new BaseOkCall(callback, client);
            call = mOkHttpClient.newCall(request);
            call.enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(BaseHttpClient client, BaseCallback callback) {
        Call call = null;
        try {
            Request request;
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams())).tag(client.getTag())
                        .delete().build();
            } else {
                request = new Request.Builder().tag(MD5Util.md5(client.getUrl()))
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(), client.getUrl(), client.getParams()))
                        .delete().build();
            }
            BaseOkCall handler = new BaseOkCall(callback, client);
            call = mOkHttpClient.newCall(request);
            call.enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postString(BaseHttpClient client, BaseCallback callback) {
        RequestBody requestBody= RequestBody.create(MediaType.parse("application/json"),client.getContent());
        Call call = null;
        Request request;
        if (client.getTag() != null) {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .tag(client.getTag())
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .post(requestBody)
                    .build();
        }
        try {
            BaseOkCall handler = new BaseOkCall(callback, client);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postFile(BaseHttpClient client, BaseCallback callback) {
        MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
        RequestBody fileBody=null;
        if (client.getParams().fileParams.size() > 0) {
            for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
                {
                    File file = entry1.getValue().file;
                    fileBody=RequestBody.create(MEDIA_TYPE_STREAM, file);
                }
            }
        }
        Call call = null;
        Request request;
        if (client.getTag() != null) {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .tag(client.getTag())
                    .post(fileBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .post(fileBody)
                    .build();
        }
        try {
            BaseOkCall handler = new BaseOkCall(callback, client);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postForm(BaseHttpClient client, BaseCallback callback) {
        RequestBody requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().urlParams.entrySet()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                    RequestBody.create(null, entry.getValue()));
        }
        if (client.getParams().fileParams.size() > 0) {
            for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
                {
                    File file = entry1.getValue().file;
                    String fileName = file.getName();
                    RequestBody fileBody = RequestBody.create(MediaType.parse(URLEncodedUtils.guessMimeType(fileName)), file);
                    //TODO 根据文件名设置contentType
                    builder.addFormDataPart(entry1.getKey(),fileName, fileBody);
                }
            }
        }
        requestBody=builder.build();
        Call call = null;
        Request request;
        if (client.getTag() != null) {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .tag(client.getTag())
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .post(requestBody)
                    .build();
        }
        try {
            BaseOkCall handler = new BaseOkCall(callback, client);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(BaseHttpClient client, BaseCallback callback) {
        Call call = null;
        try {
            Request request;
            if (client.getTag() != null) {
                request = new Request.Builder()
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(),
                                client.getUrl(), client.getParams())).tag(client.getTag())
                        .get().build();
            } else {
                request = new Request.Builder().tag(MD5Util.md5(client.getUrl()))
                        .url(URLEncodedUtils.getUrlWithQueryString(client.isShouldEncodeUrl(),
                                client.getUrl(), client.getParams()))
                        .get().build();
            }
            DownFileCall handler = new DownFileCall(BaseHttpClient.getConfiguration().getContext(),
                    callback, client.getUrl(), client.getDestFileDir(),client.getDestFileName());
            call = mOkHttpClient.newCall(request);
            call.enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postFileProgress(BaseHttpClient client, BaseCallback callback) {
        MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
        RequestBody fileBody=null;
        if (client.getParams().fileParams.size() > 0) {
            for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
                {
                    File file = entry1.getValue().file;
                    fileBody=RequestBody.create(MEDIA_TYPE_STREAM, file);
                }
            }
        }
        Call call = null;
        Request request;
        if (client.getTag() != null) {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .tag(client.getTag())
                    .post(new ProgressRequestBody(fileBody, callback))
                    .build();
        } else {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .post(new ProgressRequestBody(fileBody, callback))
                    .build();
        }
        try {
            BaseOkCall handler = new BaseOkCall(callback, client);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postFormProgress(BaseHttpClient client, BaseCallback callback) {
        RequestBody requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (ConcurrentHashMap.Entry<String, String> entry : client.getParams().urlParams.entrySet()) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                    RequestBody.create(null, entry.getValue()));
        }
        if (client.getParams().fileParams.size() > 0) {
            for (ConcurrentHashMap.Entry<String, BaseParams.FileWrapper> entry1 : client.getParams().fileParams.entrySet()) {
                {
                    File file = entry1.getValue().file;
                    String fileName = file.getName();
                    RequestBody fileBody = RequestBody.create(MediaType.parse(URLEncodedUtils.guessMimeType(fileName)), file);
                    //TODO 根据文件名设置contentType
                    builder.addFormDataPart(entry1.getKey(),fileName, fileBody);
                }
            }
        }
        requestBody=builder.build();
        Call call = null;
        Request request;
        if (client.getTag() != null) {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .tag(client.getTag())
                    .post(new ProgressRequestBody(requestBody, callback))
                    .build();
        } else {
            request = new Request.Builder()
                    .url(client.getUrl())
                    .post(new ProgressRequestBody(requestBody, callback))
                    .build();
        }
        try {
            BaseOkCall handler = new BaseOkCall(callback, client);
            mOkHttpClient.newCall(request).enqueue(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
