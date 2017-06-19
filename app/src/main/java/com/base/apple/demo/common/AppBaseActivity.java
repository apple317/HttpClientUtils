package com.base.apple.demo.common;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.apple.app.BaseActivity;

import java.util.List;



/**
 * 所有activity 的基类
 *
 * @author apple_hsp
 */
public class AppBaseActivity extends BaseActivity {


    public AppBaseActivity mBaseAct;

//    public boolean isShowLogin=false;//控制有引导页时，先显示引导页再显示登录

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mBaseAct = this;
        super.onCreate(savedInstanceState);
//        View view=initView(savedInstanceState);
//        if(view!=null)
//            setContentView(view);
        initView(savedInstanceState);
        initData(savedInstanceState);
        initLisitener();
        AppOpen();
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initLisitener() {

    }

    @Override
    protected void initStyle() {

    }



    @Override
    protected void onStop() {
        super.onStop();
    }

    public void AppFinish() {
        super.finish();
    }

    public void AppOpen() {
    }
    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void initView(Bundle bundle) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            AppFinish();
        }
        return false;
    }


    /**
     * @author apple_hsp
     * @method 定义onResume恢复方法。
     * @deprecated 定义onResume恢复方法。
     */
    protected void onActivityResume() {

    }



    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }







}
