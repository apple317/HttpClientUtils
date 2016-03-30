package com.apple.http.sample;

import com.apple.http.Listener.DownCallback;
import com.apple.http.Listener.HttpCallback;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.entity.DownEntity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;


/**
 * post 上传文件
 *
 * @author hushaoping
 */
public class DownFileActivity extends AppCompatActivity  {
    ProgressBar id_progress;
    TextView txt_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        id_progress = (ProgressBar) findViewById(R.id.id_progress);
        txt_content = (TextView) findViewById(R.id.txt_content);

    }

    public void downData(View view){
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().addUrl("http://119.188.38.18/65738BF87E9458339EB7752598/030008010056B56763489B144DDF25C34CD015-9088-4C46-DE06-105A955F87E1.mp4.ts?ts_start=5.906&ts_end=9.076&ts_seg_no=1&ts_keyframe=1===info==1")
                .downName("apple_nba").downloadFile(getApplicationContext(),new DownCallback() {
            @Override
            public void onProgress(DownEntity entity) {
                Message msg = new Message();
                msg.obj = entity;
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try{
                        DownEntity entity=(DownEntity)msg.obj;
                        Log.i("HU", "======bytes===" + entity.currentByte + "==contenLength==" + entity.totalByte);
                        id_progress.setProgress((int) (100 * (float) entity.currentByte * 1.0f / entity.totalByte));
                        txt_content.setText("当前下载的文件目录是"+entity.path+"文件名称:"+entity.name+"网络返回code"+entity.httpCode+"===服务端返回消息=="+entity.message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    };

}
