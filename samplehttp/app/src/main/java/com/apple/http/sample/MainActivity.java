package com.apple.http.sample;

import com.apple.http.common.HttpCallback;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements HttpCallback, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.txt_get).setOnClickListener(this);
        findViewById(R.id.txt_post).setOnClickListener(this);
        findViewById(R.id.txt_pull).setOnClickListener(this);
    }

    @Override
    public void onSuccess(String content, Object object, String reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, String reqType) {

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
        }
    }

    @Override
    public void onProgress(long bytesRead, long contentLength, boolean done) {

    }
}
