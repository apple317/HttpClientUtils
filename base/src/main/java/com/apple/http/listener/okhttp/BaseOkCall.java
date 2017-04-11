package com.apple.http.listener.okhttp;


import android.util.Log;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.listener.BaseCallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Such through the realization of the callback interface
 * to achieve data back to the network operation,
 * such main achieve the return data analysis, refresh the UI, notify the organizer.
 * You can access the source by the following address
 *
 * @author 胡少平
 */
public class BaseOkCall implements Callback {

    //httpcallback是自定义的请求返回对象
    BaseCallback callBack;
    //url是请求地址
    BaseHttpClient mClient;

    public BaseOkCall(BaseCallback response, BaseHttpClient client) {
        this.callBack = response;
        mClient = client;
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     */
    @Override
    public void onFailure(Call call, IOException e) {
        mClient.configuration.getHandler().post(new ErrorTask(e,mClient,call));
    }

    /**
     * Called when the HTTP response was successfully returned by the remote
     * server. The callback may proceed to read the response body with {@link
     * }. The response is still live until its response body is
     * closed with {@code response.body().close()}. The recipient of the callback
     * may even consume the response body on another thread.
     * <p/>
     * <p>Note that transport-layer success (receiving a HTTP response code,
     * headers and body) does not necessarily indicate application-layer
     * success: {@code response} may still indicate an unhappy HTTP response
     * code like 404 or 500.
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (response.isSuccessful()) {
                //成功得到文本信息
                String content = response.body().string();
                if (callBack != null) {
                    if (mClient != null ){
                        if (mClient.getParse() != null){
                            Log.e("HU","call====="+mClient.getUrlIdentifier()+"===content=="+content);
                            Object object = new Gson().fromJson(mClient.getRequestBackData()==null||mClient.getRequestBackData().trim().toString().equals("")?content:mClient.getRequestBackData().trim().toString(), mClient.getParse());
                            mClient.configuration.getHandler().post(new LoadTask(content,mClient,object));
                        }else{
                            mClient.configuration.getHandler().post(new LoadTask(content,mClient,null));
                        }
                    }
                }
            }else{
                mClient.configuration.getHandler().post(new ErrorTask(null,mClient,call));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            mClient.configuration.getHandler().post(new ErrorTask(e,mClient,null));
        }
    }




//    Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    //得到数据并去做解析类。
//                    //    BaseEntity entity = JsonPaserFactory.paserObj(msg.obj.toString(), url);
//                    //通知UI界面
//                    callBack.onSuccess(msg.obj.toString(), null, url);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };



    final class ErrorTask implements Runnable{
        private final Object call;
        private final Exception e;
        private final BaseHttpClient client;
        public ErrorTask(Exception e,BaseHttpClient mClient,Object aClass) {
            this.e = e;
            this.call = aClass;
            this.client=mClient;
        }
        @Override
        public void run() {
            if(callBack!=null)
                callBack.error(e, mClient);
        }
    }

    final class LoadTask implements Runnable{
        private final Object aClass;
        private final String paser;
        private final BaseHttpClient client;
        public LoadTask(String paser,BaseHttpClient mClient,Object aClass) {
            this.paser = paser;
            this.aClass = aClass;
            this.client=mClient;
        }
        @Override
        public void run() {
            if(callBack!=null)
             callBack.success(paser,client,aClass);
        }
    }
}
