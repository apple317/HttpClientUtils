package com.apple.http.sample;

import com.apple.http.Listener.HttpCallback;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;

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


/**
 * post 上传文件
 *
 * @author hushaoping
 */
public class DownFileActivity extends AppCompatActivity  {
    ProgressBar id_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        id_progress = (ProgressBar) findViewById(R.id.id_progress);
    }

    public void downData(View view){
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().addUrl("http://124.14.5.144/vlive.qqvideo.tc.qq.com/o00191p1ed7.m1.mp4?vkey=B77C90F73C0920ACBEEF6EB828C9268B3750196F1B8BE863A01B645746074068AC3A30C2171F6354BD38825049287C540F8A4A632F37B3496DB224F777150FC02A7992D467798AD12612ECE5D4249D920DC51ABB10552327&br=34&platform=2&fmt=msd&sdtfrom=v3010&type=mp4&locid=89489e75-bb18-40e4-989b-89d6b34adf32&size=56306437&ocid=1362567084&ocid=1614225324&locid=36f845b2-b9af-4533-af22-8b2ad4d9287f&size=73601579&ocid=290592172")
                .downName("util.xml").downloadFile(getApplicationContext(),new HttpCallback() {
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
                msg.what = 1;
                mHandler.sendMessage(msg);
                Log.i("HU","======bytes==="+bytesRead+"==contenLength=="+contentLength);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    id_progress.setProgress((int) (100 * (float)msg.obj));
                    break;
                default:
                    break;
            }
        }

    };

}
