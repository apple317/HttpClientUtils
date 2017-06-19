package com.base.apple.demo.sound.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.MainSoundBinding;
import com.base.apple.demo.R;
import com.base.apple.demo.main.view.MainActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;



/**
 * Created by kelin on 16-4-28.
 */
public class SoundViewModel extends ViewModel implements CompoundButton.OnCheckedChangeListener {


    MainSoundBinding delayBinding;
    byte[]Sendbutter;



    /**
     * 1-6
     * 1:前左边 前左增益       前左0
     * 2:后左边 后左增益       前右1
     * 3:前右边 前右增益       后左2
     * 4:后右边 后右增益       后右3
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
        int i;
        delayBinding.setVariable(BR.delay, this);
        delayList=new ArrayList<Integer>();
//        delayList.add(50);
//        delayList.add(60);
//        delayList.add(70);
//        delayList.add(80);
       for (i=0;i<4;i++){
           if (i==0){
               delayBinding.btnLfMm.setText(act.mainViewModel.CHgain.get(i)==0?"关闭":(act.mainViewModel.CHgain.get(i)+"db"));
               delayBinding.btnLfMs.setText(act.mainViewModel.CHphase.get(i)==0?"反相":"正相");
           }
           if (i==1){
               delayBinding.btnRfMm.setText(act.mainViewModel.CHgain.get(i)==0?"关闭":(act.mainViewModel.CHgain.get(i)+"db"));
               delayBinding.btnRfMs.setText(act.mainViewModel.CHphase.get(i)==0?"反相":"正相");
           }
           if (i==2){
               delayBinding.btnLbMm.setText(act.mainViewModel.CHgain.get(i)==0?"关闭":(act.mainViewModel.CHgain.get(i)+"db"));
               delayBinding.btnLbMs.setText(act.mainViewModel.CHphase.get(i)==0?"反相":"正相");
           }
           if (i==3){
               delayBinding.btnRbMm.setText(act.mainViewModel.CHgain.get(i)==0?"关闭":(act.mainViewModel.CHgain.get(i)+"db"));
               delayBinding.btnRbMs.setText(act.mainViewModel.CHphase.get(i)==0?"反相":"正相");
           }
       }
        initLintener();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getTag().toString()){
            case "1":
                Log.e("HU","onCheckedChanged==ischecked=="+isChecked);
                break;
            case "2":

                break;
            case "3":

                break;
            case "4":

                break;
        }
    }

    public void initLintener(){
        delayBinding.seekbarGain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                act.showToast("CHnum=="+type);
                switch (type){
                    case 0:
                        delayBinding.btnLfMm.setText(progress==0?"关闭":(progress+"db"));

                        break;
                    case 2:
                        delayBinding.btnLbMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                    case 1:
                        delayBinding.btnRfMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                    case 3:
                        delayBinding.btnRbMm.setText(progress==0?"关闭":(progress+"db"));
                        break;
                }
                //delayList.set(type,progress);
                act.mainViewModel.CHgain.set(type,progress);
                delayBinding.tvGainValue.setText(progress==0?"关闭":(progress+"db"));
                if (act.mainViewModel.isConnected==true){//发送通道音量
                    Sendbutter=new byte[11];
                    Sendbutter[0]=(byte)0xA5;Sendbutter[1]=(byte)0x68;Sendbutter[2]=(byte)0x35;
                    Sendbutter[3]=(byte)0x31;Sendbutter[4]=(byte)0x31;Sendbutter[5]=(byte)0x30;
                    Sendbutter[6]=(byte)0x04;Sendbutter[7]=(byte)(type+2);Sendbutter[8]=(byte)0x01;
                    Sendbutter[9]=(byte)(progress);
                    Sendbutter[10]=(byte)((Sendbutter[7]+Sendbutter[8]+Sendbutter[9]) % 256);
                    act.mainViewModel.Senddata(Sendbutter);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        delayBinding.ch1mutebtn.setOnCheckedChangeListener(this);
        delayBinding.ch2mutebtn.setOnCheckedChangeListener(this);
        delayBinding.ch3mutebtn.setOnCheckedChangeListener(this);
        delayBinding.ch4mutebtn.setOnCheckedChangeListener(this);

        delayBinding.ch1mutebtn.setTag("1");
        delayBinding.ch2mutebtn.setTag("2");
        delayBinding.ch3mutebtn.setTag("3");
        delayBinding.ch4mutebtn.setTag("4");
    }


    MainActivity act;

    public SoundViewModel(MainActivity activity) {
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
    * 发送相位
   */
    public  void Sendphasedata(){
        act.mainViewModel.showToast("发送相位");
        if (act.mainViewModel.isConnected==true){//发送通道音量
            Sendbutter=new byte[11];
            Sendbutter[0]=(byte)0xA5;Sendbutter[1]=(byte)0x68;Sendbutter[2]=(byte)0x35;
            Sendbutter[3]=(byte)0x31;Sendbutter[4]=(byte)0x31;Sendbutter[5]=(byte)0x30;
            Sendbutter[6]=(byte)0x04;Sendbutter[7]=(byte)(type+2);Sendbutter[8]=(byte)0x0B;
            Sendbutter[9]=(byte)act.mainViewModel.CHphase.get(type).intValue();
            Sendbutter[10]=(byte)((Sendbutter[7]+Sendbutter[8]+Sendbutter[9]) % 256);
            act.mainViewModel.Senddata(Sendbutter);
        }else{
            return;
        }
    }
    /**
     * 延迟down－ms按下操作
     */
    public void onDelayDownMs(View view) {
        type=6;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvGainValue.setText(delayList.get(type)==0?"关闭":(delayList.get(type)+"db"));

    }


    /**
     * 延迟down－mm按下操作
     */
    public void onDelayDownMM(View view) {
        type=6;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("重低音延时");
        delayBinding.tvGainValue.setText(delayList.get(type)==0?"关闭":(delayList.get(type)+"db"));
        delayBinding.seekbarGain.setProgress(delayList.get(type));
    }


    /**
     * 后右-相位
     */
    public void Rbphase(View view) {
        type=3;act.mainViewModel.CHnum=type;
        act.mainViewModel.CHphase.set(3,act.mainViewModel.CHphase.get(3)==0?127:0);
        delayBinding.btnLbMs.setText(act.mainViewModel.CHphase.get(3)==0?"反相":"正相");
        Sendphasedata();
    }


    /**
     * 后右-增益
     */
    public void onDelayRbMM(View view) {
        type=3;act.mainViewModel.CHnum=type;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后右增益");
        delayBinding.tvGainValue.setText(act.mainViewModel.CHgain.get(type)==0?"关闭":(act.mainViewModel.CHgain.get(type)+"db"));
        delayBinding.seekbarGain.setProgress(act.mainViewModel.CHgain.get(type));
    }

    /**
     * 后左-相位
     */
    public void Lbphase(View view) {
        type=2;act.mainViewModel.CHnum=type;
        act.mainViewModel.CHphase.set(2,act.mainViewModel.CHphase.get(2)==0?127:0);
        delayBinding.btnLbMs.setText(act.mainViewModel.CHphase.get(2)==0?"反相":"正相");
        Sendphasedata();
    }


    /**
     * 后左-增益
     */
    public void onDelayLbMM(View view) {
        type=2;act.mainViewModel.CHnum=type;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("后左增益");
        delayBinding.tvGainValue.setText(act.mainViewModel.CHgain.get(type)==0?"关闭":(act.mainViewModel.CHgain.get(type)+"db"));
        delayBinding.seekbarGain.setProgress(act.mainViewModel.CHgain.get(type));
    }

    /**
     * 前左－相位-按下操作
     */
    public void Lfphase(View view) {
        type=0;act.mainViewModel.CHnum=type;
        act.mainViewModel.showToast("左前相位");
        act.mainViewModel.CHphase.set(0,act.mainViewModel.CHphase.get(0)==0?127:0);
        delayBinding.btnLfMs.setText(act.mainViewModel.CHphase.get(0)==0?"反相":"正相");
        Sendphasedata();
    }

    /**
     * 前左-增益
     */
    public void onDelayLfMM(View view) {
        type=0;act.mainViewModel.CHnum=type;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前左增益");
        delayBinding.tvGainValue.setText(act.mainViewModel.CHgain.get(type)==0?"关闭":(act.mainViewModel.CHgain.get(type)+"db"));
        delayBinding.seekbarGain.setProgress(act.mainViewModel.CHgain.get(type));
    }


    /**
     * 前右-相位按下操作
     */
    public void Rfphase(View view) {
        act.mainViewModel.showToast("前右相位");
        type=1;act.mainViewModel.CHnum=type;
        act.mainViewModel.CHphase.set(1,act.mainViewModel.CHphase.get(1)==0?127:0);
        delayBinding.btnRfMs.setText(act.mainViewModel.CHphase.get(1)==0?"反相":"正相");
        Sendphasedata();
    }


    /**
     * 前右边－增益
     */
    public void onDelayRfMM(View view) {
        type=1;act.mainViewModel.CHnum=type;
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("前右增益");
        delayBinding.tvGainValue.setText(act.mainViewModel.CHgain.get(type)==0?"关闭":(act.mainViewModel.CHgain.get(type)+"db"));
        delayBinding.seekbarGain.setProgress(act.mainViewModel.CHgain.get(type));

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
