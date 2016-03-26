package com.apple.http.sample;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.common.BaseParams;
import com.apple.http.common.HttpCallback;
import com.squareup.okhttp.Call;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * get 请求数据
 * @author  hushaoping
 */
public class GetActivity extends AppCompatActivity implements HttpCallback,View.OnClickListener{

    TextView txt_content;
    BaseParams mParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        findViewById(R.id.txt_get).setOnClickListener(this);
        txt_content= (TextView)findViewById(R.id.txt_content);
        initData();
    }

    @Override
    public void onSuccess(String content, Object object, String reqType) {
        Log.i("HU","onSuccess=====content=="+content);
        txt_content.setText(content+"type==="+reqType);
    }

    @Override
    public void onFailure(Throwable error, String content, String reqType) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_get:
                initData();
                break;
        }
    }

    /**
     * 请求数据
     */
    void initData(){
        initParameter();
        mParams.put("appkey", "56065429");
        mParams.put("sign", "AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB");
        Call call=BaseHttpClient.getBaseClient(getApplicationContext()).sendGetRequest("http://api.dianping.com/v1/metadata/get_cities_with_deals", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
       call.cancel();
       Log.i("HU","=====cancel=="+call.isCanceled());
    }
    /**
     * 初始化参数
     */
    protected  void initParameter(){
        if (mParams == null) {
            mParams = new BaseParams();
        }else {
            mParams.clear();
        }
    }

}
