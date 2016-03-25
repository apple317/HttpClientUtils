package com.apple.http.common;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import com.squareup.okhttp.Callback;


/**
 * Such through the realization of the callback interface
 * to achieve data back to the network operation,
 * such main achieve the return data analysis, refresh the UI, notify the organizer.
 You can access the source by the following address
 @author 胡少平
 */
public class BaseOkHandler implements Callback {

    //httpcallback是自定义的请求返回对象
    HttpCallback callBack;
    //url是请求地址
    String url;
    Object parseObject;
    public BaseOkHandler(HttpCallback response,String requestUrl,Object object) {
        this.callBack = response;
        url = requestUrl;
        parseObject=object;
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     */
    @Override
    public void onFailure(Request request, IOException e) {
        Log.i("HU", "handle==onFailure=REQ_GET_INIT=reqType=");

    }
    /**
     * Called when the HTTP response was successfully returned by the remote
     * server. The callback may proceed to read the response body with {@link
     * Response#body}. The response is still live until its response body is
     * closed with {@code response.body().close()}. The recipient of the callback
     * may even consume the response body on another thread.
     *
     * <p>Note that transport-layer success (receiving a HTTP response code,
     * headers and body) does not necessarily indicate application-layer
     * success: {@code response} may still indicate an unhappy HTTP response
     * code like 404 or 500.
     */
    @Override
    public void onResponse(Response response) throws IOException {
        try {
            if (response.isSuccessful()) {
                //成功得到文本信息
                String content = response.body().string();
                //通过Handler来传给UI线程。
                Message msg =new Message();
                msg.obj = content;
                msg.what=0;
                mHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //得到数据并去做解析类。
                //    BaseEntity entity = JsonPaserFactory.paserObj(msg.obj.toString(), url);
                    //通知UI界面
                    callBack.onSuccess(msg.obj.toString(), null, url);
                    break;
                default:
                    break;
            }
        }

    };
}
