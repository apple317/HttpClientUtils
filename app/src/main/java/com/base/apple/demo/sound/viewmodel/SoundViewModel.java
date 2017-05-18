package com.base.apple.demo.sound.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.MainSoundBinding;
import com.base.apple.demo.R;

import java.util.ArrayList;

import butterknife.ButterKnife;



/**
 * Created by kelin on 16-4-28.
 */
public class SoundViewModel extends ViewModel {


    MainSoundBinding delayBinding;




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

    private ArrayList<Integer> delayList;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_sound, null);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;

    }

    @Override
    public void initData(Bundle bundle) {
        delayBinding.setVariable(BR.delay, this);
        delayList=new ArrayList<Integer>();
        delayList.add(0);
        delayList.add(0);
        delayList.add(0);
        delayList.add(0);
        initLintener();
    }

    public void initLintener(){
        delayBinding.seekbarGain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (type){
                    case 1:
                        delayBinding.btnLfMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                    case 2:
                        delayBinding.btnLbMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                    case 3:
                        delayBinding.btnRfMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                    case 4:
                        delayBinding.btnRbMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                }
                delayList.set(type-1,progress);
                delayBinding.tvGainValue.setText(progress==0?"关闭":(progress+"db"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    Activity act;

    public SoundViewModel(Activity activity) {
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
        type=6;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayDownMM(View view) {
        type=6;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayRbMs(View view) {
        type=4;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayRbMM(View view) {
        type=4;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));
    }




    /**
     * 左下角控制－ms按下操作
     */
    public void onDelayLbMs(View view) {
        type=2;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 左下角控制－mm按下操作
     */
    public void onDelayLbMM(View view) {
        type=2;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));
    }




    /**
     * 前左边－ms按下操作
     */
    public void onDelayLfMs(View view) {
        type=1;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayLfMM(View view) {
        type=1;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));
    }


    /**
     * 前左边－ms按下操作
     */
    public void onDelayRfMs(View view) {
        type=3;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayRfMM(View view) {
        type=3;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));

    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayUpMs(View view) {
        type=5;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayUpMM(View view) {
        type=5;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delayBinding.tvGainValue.setText(delayList.get(type-1)==0?"关闭":(delayList.get(type-1)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type-1));
    }



}
