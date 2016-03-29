package com.apple.http.sample;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.common.HttpCallback;
import com.apple.http.utils.FileNameGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

/**
 * post 请求数据
 *
 * @author hushaoping
 */
public class PostActivity extends AppCompatActivity{

    TextView txt_content;
    BaseParams mParams;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        txt_content = (TextView) findViewById(R.id.txt_content);
        mProgressBar = (ProgressBar) findViewById(R.id.id_progress);
    }


    /**
     * post
     * 单个文件上传
     */
    public void postFormFile(View view) {
        try {
            /**
             * 先初始化参数
             */
            initParameter();
            mParams.put("game", "lol");
            mParams.put("os", "2");
            mParams.put("device_id", "123123124");
            mParams.put("version", "1.2.0");
            mParams.put("token", "c75db8c42e3bfbc2b0e0ab28b9d933792e63d918");
            File file = new File(Environment.getExternalStorageDirectory(), "4cc75752fa532553bf7b6f7e00f26db8.png");
            try {
                mParams.put("logo", file, FileNameGenerator.getMIMEType(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
            BaseHttpClient.getBaseClient().addUrl("url").setBaseParams(mParams)
                    .postRequest(new HttpCallback() {
                        @Override
                        public void onSuccess(String content, Object object, String reqType) {
                            Message msg = new Message();
                            msg.obj = content;
                            msg.what = 0;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(Throwable error, String content, String reqType) {
                            Log.i("HU", "onFailure=content=" + content);
                            Log.i("HU", "onFailure=error=" + error);


                        }

                        @Override
                        public void onProgress(long bytesRead, long contentLength, boolean done) {
                            Log.i("HU", "onprogress=bytesRead=" + bytesRead);
                            Message msg = new Message();
                            msg.obj = bytesRead * 1.0f / contentLength;
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 普通post请求数据
     * 默认以表单提交
     */
    public void postData(View view) {
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().addUrl("url")
                .put("device_id", "1236716").put("os", "2").put("version", "2.1").put("mobile", "123736467").postRequest(new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {
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
                Message msg = new Message();
                msg.obj = bytesRead * 1.0f / contentLength;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        });


    }


    /**
     * post上传字符串
     */
    public void postStringData(View view) {
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().addUrl("url")
                .put("apple_txt", "你好好好").postStringRequest(new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {
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
     * 初始化参数
     */
    protected void initParameter() {
        if (mParams == null) {
            mParams = new BaseParams();
        } else {
            mParams.clear();
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
                    JSONObject object= null;
                    try {
                        object = new JSONObject(msg.obj.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject statuObj=object.optJSONObject("status");
                    if(statuObj!=null){
                        txt_content.setText(statuObj.optString("message") + "statue==="+statuObj.optInt("code"));

                    }
                   // txt_content.setText(statuObj.optString("message") + "type===");

                    break;
                case 1:
                    mProgressBar.setProgress((int) (100 * (float)msg.obj));
                    break;
                default:
                    break;
            }
        }

    };
}
