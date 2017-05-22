package com.base.apple.demo.main.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.base.apple.demo.R;
import com.base.apple.demo.common.AppBaseActivity;


/**
 * Created by zhangliang on 16/4/7. 新版启动页
 */
public class SplashActivity extends AppBaseActivity {


    @Override
    public void initView(Bundle bundle) {
        super.initView(bundle);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(runnable, 3000); // 开始Timer
    }


    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    };






    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
    }

}
