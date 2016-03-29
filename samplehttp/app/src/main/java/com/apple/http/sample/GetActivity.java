package com.apple.http.sample;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.common.HttpCallback;
import com.apple.http.utils.FileNameGenerator;
import com.squareup.okhttp.Call;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import okhttp3.MediaType;

/**
 * get 请求数据
 *
 * @author hushaoping
 */
public class GetActivity extends AppCompatActivity{

    TextView txt_content;
    BaseParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        txt_content = (TextView) findViewById(R.id.txt_content);
    }




    /**
     * 普通参数请求方式
     */
    public void getParam(View view) {
        BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                .put("appkey", "56065429").put("sign", "AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB").setTag("deals").getRequest(new HttpCallback() {
            @Override
            public void onSuccess(final String content, Object object, final String reqType) {
                Message msg = new Message();
                msg.obj = content;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }

            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    /**
     * 普通参数请求方式
     */
    public void getParamXml(View view) {
        BaseHttpClient.getBaseClient().addUrl("http://sec.mobile.tiancity.com/server/mobilesecurity/version.xml").setTag("deals").getRequest(new HttpCallback() {
            @Override
            public void onSuccess(final String content, Object object, final String reqType) {
                Message msg =new Message();
                msg.obj = content;
                msg.what=0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }

            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
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
//                    application/octet-stream
//                    multipart/form-data
                    MediaType FORM=MediaType.parse("text/plain;charset=utf-8");
                    txt_content.setText(msg.obj.toString() + "type==="+FORM.subtype());

                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 初始化参数
     */
    protected void initParameter() {
        if (mParams == null) {
            mParams = new BaseParams();
        } else {
            mParams.clear();
        }
    }

}
