package com.base.apple.demo.delay.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.MainDelayBinding;
import com.base.apple.demo.R;

import butterknife.ButterKnife;


/**
 * Created by kelin on 16-4-28.
 */
public class DelayViewModel extends ViewModel {


    MainDelayBinding delayBinding;


    private double delay;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_delay, null);
        ButterKnife.bind(view);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;

    }

    @Override
    public void initData(Bundle bundle) {
        delayBinding.setVariable(BR.delay, this);
        delayBinding.btnDownMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnDownMs.setText(String.format("%.2fms",delay));

        delayBinding.btnLbMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnLbMs.setText(String.format("%.2fms",delay));

        delayBinding.btnLfMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnLfMs.setText(String.format("%.2fms",delay));

        delayBinding.btnRbMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnRbMs.setText(String.format("%.2fms",delay));

        delayBinding.btnRfMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnRfMs.setText(String.format("%.2fms",delay));

        delayBinding.btnUpMm.setText(String.format("%.2fmm",delay));
        delayBinding.btnUpMs.setText(String.format("%.2fms",delay));

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
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayDownMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayRbMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayRbMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     * 左下角控制－ms按下操作
     */
    public void onDelayLbMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 左下角控制－mm按下操作
     */
    public void onDelayLbMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     * 前左边－ms按下操作
     */
    public void onDelayLfMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayLfMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 前左边－ms按下操作
     */
    public void onDelayRfMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayRfMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayUpMs(View view) {
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delay=0.0;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayUpMM(View view) {
        delay=0.0;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }




    /**
     *延迟控制减少
     */
    public void onDelayParamSub(View view) {
        delay=delay-0.1;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }



    /**
     *延迟控制添加
     */
    public void onDelayParamAdd(View view) {
        delay=delay+0.1;
        delayBinding.tvParm.setText(String.format("%.2fms",delay));
    }
}
