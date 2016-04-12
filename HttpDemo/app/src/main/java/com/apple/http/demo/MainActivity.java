package com.apple.http.demo;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.HttpConfiguration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.txt_get).setOnClickListener(this);
        findViewById(R.id.txt_post).setOnClickListener(this);
        findViewById(R.id.txt_pull).setOnClickListener(this);
        findViewById(R.id.txt_download).setOnClickListener(this);
        initHttpClient();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_get:
                Intent intent=new Intent(getApplicationContext(),GetActivity.class);
                startActivity(intent);
                break;
            case R.id.txt_post:
                Intent intent1=new Intent(getApplicationContext(),PostActivity.class);
                startActivity(intent1);
                break;
            case R.id.txt_pull:
                Intent intent2=new Intent(getApplicationContext(),PullActivity.class);
                startActivity(intent2);
                break;
            case R.id.txt_download:
                Intent intent3=new Intent(getApplicationContext(),DownFileActivity.class);
                startActivity(intent3);
                break;
        }
    }

    /**
     * 网络初始化设置
     * 设置缓冲大小
     * 设置缓冲路径
     * 设置超时读写时间
     *
     */
    void initHttpClient(){
        HttpConfiguration.Builder configuration=new HttpConfiguration.Builder(getApplicationContext());
        configuration.connectTimeout(2000);
        configuration.retryOnConnectionFailure(true);
        configuration.readTimeout(2000);
        configuration.writeTimeout(2000);
        configuration.diskCacheSize(1000 * 1024);
        configuration.diskCacheDir(getCacheDir());
        BaseHttpClient.getBaseClient().init(configuration.build());
    }



}
