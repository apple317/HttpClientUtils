package com.apple.http.sample;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.common.HttpCallback;
import com.apple.http.utils.FileNameGenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * post 上传文件
 *
 * @author hushaoping
 */
public class PostSubFileActivity extends AppCompatActivity implements HttpCallback, View.OnClickListener {
    TextView txt_content;
    BaseParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sub_file);
        txt_content = (TextView) findViewById(R.id.txt_content);
        findViewById(R.id.txt_get).setOnClickListener(this);
    }

    @Override
    public void onSuccess(String content, Object object, String reqType) {
        txt_content.setText(content + "type===" + reqType);

    }

    @Override
    public void onFailure(Throwable error, String content, String reqType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_get:
                initData();
                break;
        }
    }

    /**
     * 请求数据
     */
    void initData() {
        initParameter();
        mParams.put("os", "2");
        File file = new File("app.png");
        try {
            //logo 是文件上传定义的名词，根据服务端定义调整
            mParams.put("logo", file, FileNameGenerator.getMIMEType(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        BaseHttpClient.getBaseClient(getApplicationContext()).sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
//            @Override
//            public void onSuccess(String content, Object object, String reqType) {
//                txt_content.setText(content + "type===" + reqType);
//
//            }
//
//            @Override
//            public void onFailure(Throwable error, String content, String reqType) {
//
//            }
//        });
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
}
