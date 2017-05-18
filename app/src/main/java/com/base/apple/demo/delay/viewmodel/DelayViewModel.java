package com.base.apple.demo.delay.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.MainDelayBinding;
import com.base.apple.demo.R;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * Created by kelin on 16-4-28.
 */
public class DelayViewModel extends ViewModel {


    MainDelayBinding delayBinding;


    /**
     * 1-6
     * 1:前左边 前左增益
     * 2:后左边 后左增益
     * 3:前右边 前右增益
     * 4:后右边 后右增益
     * 5:中置，顶中心
     * 6:底下中心 重低音增益
     */

    int type=-1;
    private ArrayList<Double> delayList;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_delay, null);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;

    }

    @Override
    public void initData(Bundle bundle) {
        delayBinding.setVariable(BR.delay, this);
        delayList=new ArrayList<Double>();
        delayList.add(0.0);
        delayList.add(0.0);
        delayList.add(0.0);
        delayList.add(0.0);
        delayBinding.btnLbMs.setText(String.format("%.2fms",delayList.get(1)));
        delayBinding.btnLfMs.setText(String.format("%.2fms",delayList.get(0)));
        delayBinding.btnRbMs.setText(String.format("%.2fms",delayList.get(3)));
        delayBinding.btnRfMs.setText(String.format("%.2fms",delayList.get(2)));
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
    }


    Activity act;

    public DelayViewModel(Activity activity) {
        this.act = activity;
    }


    @Override
    public void success(String content, BaseHttpClient client, Object parse) {
        super.success(content, client, parse);
        switch (client.getUrlIdentifier()) {

        }
    }

    @Override
    public void error(Throwable error, BaseHttpClient client) {
        super.error(error, client);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayDownMs(View view) {
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("重低音延时");
//        delay=0.0;
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayDownMM(View view) {
//        delay=0.0;
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("重低音延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayRbMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右延时");
        type=4;
        delayBinding.tvParm.setText(String.format("%.1fms",delayList.get(type-1)));
        delayBinding.btnRbMs.setText(String.format("%.2fms",delayList.get(type-1)));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayRbMM(View view) {
//        delay=0.0;
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("后右延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     * 左下角控制－ms按下操作
     */
    public void onDelayLbMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左延时");
        type=2;
        delayBinding.tvParm.setText(String.format("%.1fms",delayList.get(type-1)));
        delayBinding.btnLbMs.setText(String.format("%.2fms",delayList.get(type-1)));
    }


    /**
     * 左下角控制－mm按下操作
     */
    public void onDelayLbMM(View view) {
//        delay=0.0;
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("后左延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     * 前左边－ms按下操作
     */
    public void onDelayLfMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左延时");
        type=1;
        delayBinding.tvParm.setText(String.format("%.2fms",delayList.get(type-1)));
        delayBinding.btnLfMs.setText(String.format("%.2fms",delayList.get(type-1)));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayLfMM(View view) {
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("前左延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 前右延时－ms按下操作
     */
    public void onDelayRfMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右延时");
        type=3;
        delayBinding.tvParm.setText(String.format("%.1fms",delayList.get(type-1)));
        delayBinding.btnRfMs.setText(String.format("%.2fms",delayList.get(type-1)));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayRfMM(View view) {
//        delay=0.0;
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("前右延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayUpMs(View view) {
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("中置延时");
//        delay=0.0;
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayUpMM(View view) {
//        delay=0.0;
//        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
//        delayBinding.tvParmTitle.setText("中置延时");
//        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     *延迟控制减少
     */
    public void onDelayParamSub(View view) {
        double delay=delayList.get(type-1);
        delayList.set(type-1,delay<0.1?0:(delay-0.1));
        delayBinding.tvParm.setText(String.format("%.1fms",delay));
        switch (type){
            case 1:
                delayBinding.btnLfMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 2:
                delayBinding.btnLbMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 3:
                delayBinding.btnRfMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 4:
                delayBinding.btnRbMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
        }
    }



    /**
     *延迟控制添加
     */
    public void onDelayParamAdd(View view) {
        double delay=delayList.get(type-1);
        delayList.set(type-1,delay>9.9?10:(delay+0.1));
        delayBinding.tvParm.setText(String.format("%.1fms",delay));
        switch (type){
            case 1:
                delayBinding.btnLfMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 2:
                delayBinding.btnLbMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 3:
                delayBinding.btnRfMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
            case 4:
                delayBinding.btnRbMs.setText(String.format("%.2fms",delayList.get(type-1)));
                break;
        }
    }
}
