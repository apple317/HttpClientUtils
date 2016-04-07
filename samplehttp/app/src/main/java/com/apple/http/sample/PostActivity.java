package com.apple.http.sample;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.entity.METHOD;
import com.apple.http.listener.HttpCallback;
import com.apple.http.listener.UploadCallback;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


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
     * 表单文件上传
     * 默认以表单提交
     * addUrl传入url地址
     * put传入参数可以
     *setTag 设置tag
     * put 传入logo为文件上传定义接口名称 value:为文件对象
     * setBaseParams 通过这样的传入参数对象
     * 上传文件onProgress 会返回当前网络上传的进度
     * POST_FORM 是普通表单提交
     */
    public void postFormFile(View view) {
        try {
            File file=new File("path");
            ArrayList<File> arrayList=new ArrayList<File>();
            arrayList.add(file);
//            File file = new File(Environment.getExternalStorageDirectory()
//                    , "4cc75752fa532553bf7b6f7e00f26db8.png");
            BaseHttpClient.getBaseClient().newBuilder()
                    .url("url")
                    .put("game", "lol")
                    .put("logo", arrayList)
                    .put("token","ee595bd5078a6e67a110c6bd8828c8e2a2388c12")
                    .put("version", "2.1.0")
                    .put("device_id","867905026687709")
                    .put("os","2")
                    .method(METHOD.POST_FORM).build()
                    .execute(new HttpCallback() {
                        @Override
                        public void onSuccess(String content, BaseHttpClient object, Object parse) {
                                Message msg = new Message();
                                msg.obj = content;
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Throwable error, BaseHttpClient client) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * post
     * 表单文件上传
     * 默认以表单提交
     * addUrl传入url地址
     * put传入参数可以
     *setTag 设置tag
     * put 传入logo为文件上传定义接口名称 value:为文件对象 也可以为文件数组对象
     * setBaseParams 通过这样的传入参数对象
     * 类型一定要传入POST_FORM_PROGRESS
     * 上传文件onProgress 会返回当前网络上传的进度
     */
    public void postFormFileProgress(View view) {
        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    , "4cc75752fa532553bf7b6f7e00f26db8.png");
            BaseHttpClient.getBaseClient().newBuilder().url("url")
                    .put("game", "lol")
                    .put("logo", file)
                    .put("token","ee595bd5078a6e67a110c6bd8828c8e2a2388c12")
                    .put("version", "2.1.0")
                    .put("device_id","867905026687709")
                    .put("os","2")
                    .method(METHOD.POST_FORM_PROGRESS).build()
                    .execute(new UploadCallback() {
                        @Override
                        public void onSuccess(String content, BaseHttpClient object, Object parse) {
                            Message msg = new Message();
                            msg.obj = parse;
                            msg.what = 0;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onError(Throwable error, BaseHttpClient client) {

                        }

                        @Override
                        public void uploadProgress(long bytesRead, long contentLength, boolean done) {
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
     * addUrl传入url地址
     * put传入参数可以
     *setTag 设置tag
     */
    public void postData(View view) {
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().newBuilder()
                .url("url")
                .put("device_id", "1236716")
                .setTag("postactivity").put("os", "2")
                .put("version", "2.1.0").
                put("mobile", "13621937708").method(METHOD.POST_FORM)
                .build().execute(new HttpCallback() {
            @Override
            public void onSuccess(String content, BaseHttpClient object, Object parse) {
                Message msg = new Message();
                msg.obj = parse;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
            @Override
            public void onError(Throwable error, BaseHttpClient client) {

            }
        });
    }


    /**
     * post上传字符串
     * addUrl传入url地址
     * put传入参数可以
     * apple_txt 这个是专门用来传入文本上传的，请注意
     */
    public void postStringData(View view) {
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().newBuilder().url("url")
                .content("你好好好").method(METHOD.POST_STRING)
                .build().execute(new HttpCallback() {
            @Override
            public void onSuccess(String content, BaseHttpClient object, Object parse) {
                Message msg = new Message();
                msg.obj = parse;
                msg.what = 0;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onError(Throwable error, BaseHttpClient client) {

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
