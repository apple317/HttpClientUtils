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

import butterknife.ButterKnife;



/**
 * Created by kelin on 16-4-28.
 */
public class SoundViewModel extends ViewModel {


    MainSoundBinding delayBinding;


    int lbDb=60,lfDb=60,rbDb=60,rfDb=60,downDb=60,UpDb=60;


    /**
     * 1-6
     * 1:中置，顶中心
     * 2:前左边 前左增益
     * 3:后左边 后左增益
     * 4:前右边 前右增益
     * 5:后右边 后右增益
     * 6:底下中心 重低音增益
     */
    int type=0;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_sound, null);
        ButterKnife.bind(view);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;

    }

    @Override
    public void initData(Bundle bundle) {
        delayBinding.setVariable(BR.delay, this);
        initLintener();
    }

    public void initLintener(){
        delayBinding.seekbarGain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (type){
                    case 1:
                        UpDb=progress;
                        break;
                    case 2:
                        lfDb=progress;
                        break;
                    case 3:
                        lbDb=progress;
                        break;
                    case 4:
                        rfDb=progress;
                        break;
                    case 5:
                        rbDb=progress;
                        break;
                    case 6:
                        downDb=progress;
                        break;
                }
                delayBinding.tvGainValue.setText(progress==60?"关闭":(-progress+"db"));
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
        delayBinding.tvGainValue.setText(downDb==60?"关闭":(downDb+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayDownMM(View view) {
        type=6;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvGainValue.setText(downDb==60?"关闭":(downDb+"db"));
        delayBinding.seekbarGain.setProgress(downDb);
    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayRbMs(View view) {
        type=5;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvGainValue.setText(rbDb==60?"关闭":(rbDb+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayRbMM(View view) {
        type=5;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvGainValue.setText(rbDb==60?"关闭":(rbDb+"db"));
        delayBinding.seekbarGain.setProgress(rbDb);
    }




    /**
     * 左下角控制－ms按下操作
     */
    public void onDelayLbMs(View view) {
        type=3;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvGainValue.setText(lbDb==60?"关闭":(rbDb+"db"));
    }


    /**
     * 左下角控制－mm按下操作
     */
    public void onDelayLbMM(View view) {
        type=3;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvGainValue.setText(lbDb==60?"关闭":(rbDb+"db"));
        delayBinding.seekbarGain.setProgress(lbDb);
    }




    /**
     * 前左边－ms按下操作
     */
    public void onDelayLfMs(View view) {
        type=2;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvGainValue.setText(lfDb==60?"关闭":(lfDb+"db"));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayLfMM(View view) {
        type=2;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvGainValue.setText(lfDb==60?"关闭":(lfDb+"db"));
        delayBinding.seekbarGain.setProgress(lfDb);
    }


    /**
     * 前左边－ms按下操作
     */
    public void onDelayRfMs(View view) {
        type=4;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvGainValue.setText(rfDb==60?"关闭":(rfDb+"db"));
    }


    /**
     * 前左边－－mm按下操作
     */
    public void onDelayRfMM(View view) {
        type=4;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvGainValue.setText(rfDb==60?"关闭":(rfDb+"db"));
        delayBinding.seekbarGain.setProgress(rfDb);

    }


    /**
     * 延迟down－ms按下操作
     */
    public void onDelayUpMs(View view) {
        type=1;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delayBinding.tvGainValue.setText(UpDb==60?"关闭":(UpDb+"db"));
    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayUpMM(View view) {
        type=1;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("中置延迟");
        delayBinding.tvGainValue.setText(UpDb==60?"关闭":(UpDb+"db"));
        delayBinding.seekbarGain.setProgress(UpDb);
    }



}
