package com.apple.http.demo;

import com.apple.http.common.BaseParams;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import okhttp3.Cookie;


/**
 * post 上传文件
 *
 * @author hushaoping
 */
public class DelActivity extends AppCompatActivity  {
    TextView txt_content;
    BaseParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        txt_content = (TextView) findViewById(R.id.txt_content);
    }



}
