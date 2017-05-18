package com.base.apple.demo.eq.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.DemooBean;
import com.base.apple.demo.MainEqBinding;
import com.base.apple.demo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by kelin on 16-4-28.
 */
public class EqViewModel extends ViewModel implements SeekBar.OnSeekBarChangeListener {


    MainEqBinding delayBinding;

    /**
     * 1-6
     * 1:前左边 前左增益
     * 2:后左边 后左增益
     * 3:前右边 前右增益
     * 4:后右边 后右增益
     * 5:中置，顶中心
     * 6:底下中心 重低音增益
     */
    int type=1;


    /**
     * eq设置db
     * 0-400
     * -20==20db
     */
    ArrayList<Integer> setEq;


    /**
     * eq设置Hz
     * 2855-0
     */
    ArrayList<Integer> setHz;

    /**
     * eq设置QL
     * 0.3-15 0.1
     */
    ArrayList<Double> setQl;

    public void initLintener(){
        delayBinding.trackSeekbar1.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar2.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar3.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar4.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar5.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar6.setOnSeekBarChangeListener(this);
        delayBinding.trackSeekbar7.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.track_seekbar1:
                setEq.set(7*(type-1)+0,progress);
                delayBinding.tvChGain1.setText(String.format("%ddB",(setEq.get(7*(type-1)+0)-200)/10));
                break;
            case R.id.track_seekbar2:
                setEq.set(7*(type-1)+1,progress);
                delayBinding.tvChGain2.setText(String.format("%ddB",(setEq.get(7*(type-1)+1)-200)/10));
                break;
            case R.id.track_seekbar3:
                setEq.set(7*(type-1)+2,progress);
                delayBinding.tvChGain3.setText(String.format("%ddB",(setEq.get(7*(type-1)+2)-200)/10));
                break;
            case R.id.track_seekbar4:
                setEq.set(7*(type-1)+3,progress);
                delayBinding.tvChGain4.setText(String.format("%ddB",(setEq.get(7*(type-1)+3)-200)/10));
                break;
            case R.id.track_seekbar5:
                setEq.set(7*(type-1)+4,progress);
                delayBinding.tvChGain5.setText(String.format("%ddB",(setEq.get(7*(type-1)+4)-200)/10));
                break;
            case R.id.track_seekbar6:
                setEq.set(7*(type-1)+5,progress);
                delayBinding.tvChGain6.setText(String.format("%ddB",(setEq.get(7*(type-1)+5)-200)/10));

                break;
            case R.id.track_seekbar7:
                setEq.set(7*(type-1)+6,progress);
                delayBinding.tvChGain7.setText(String.format("%ddB",(setEq.get(7*(type-1)+6)-200)/10));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_eq, null);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;
    }

    @Override
    public void initData(Bundle bundle) {
        setEq=new ArrayList<Integer>();
        setHz=new ArrayList<Integer>();
        setQl=new ArrayList<Double>();
        delayBinding.setVariable(BR.highlow, this);
        for(int i=0;i<28;i++){
            setQl.add(0.3);
            setHz.add(i*40);
            setEq.add(200);
        }
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        updateView();
        initLintener();
    }


    Activity act;

    public EqViewModel(Activity activity) {
        this.act = activity;
    }





    @Override
    public void success(String content, BaseHttpClient client, Object parse) {
        super.success(content, client, parse);
        switch (client.getUrlIdentifier()) {

        }
    }

    /**
     * 更新界面数据
     */
    void updateView(){
        delayBinding.tvFrontLeft.setSelected(false);
        delayBinding.tvFrontRight.setSelected(false);
        delayBinding.tvAfterLeft.setSelected(false);
        delayBinding.tvAfterRight.setSelected(false);
        switch (type){
            case 1:
                delayBinding.tvFrontLeft.setSelected(true);
                break;
            case 2:
                delayBinding.tvAfterLeft.setSelected(true);
                break;
            case 3:
                delayBinding.tvFrontRight.setSelected(true);
                break;
            case 4:
                delayBinding.tvAfterRight.setSelected(true);
                break;
        }
        delayBinding.btnChQ1.setText(setQl.get(7*(type-1)+0)+"");
        delayBinding.btnChQ2.setText(setQl.get(7*(type-1)+1)+"");
        delayBinding.btnChQ3.setText(setQl.get(7*(type-1)+2)+"");
        delayBinding.btnChQ4.setText(setQl.get(7*(type-1)+3)+"");
        delayBinding.btnChQ5.setText(setQl.get(7*(type-1)+4)+"");
        delayBinding.btnChQ6.setText(setQl.get(7*(type-1)+5)+"");
        delayBinding.btnChQ7.setText(setQl.get(7*(type-1)+6)+"");

        delayBinding.btnChFreq1.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+0))));
        delayBinding.btnChFreq2.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+1))));
        delayBinding.btnChFreq3.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+2))));
        delayBinding.btnChFreq4.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+3))));
        delayBinding.btnChFreq5.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+4))));
        delayBinding.btnChFreq6.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+5))));
        delayBinding.btnChFreq7.setText(String.format("%dHz",intPin(setHz.get(7*(type-1)+6))));

        delayBinding.tvChGain1.setText(String.format("%ddB",(setEq.get(7*(type-1)+0)-200)/10));
        delayBinding.tvChGain2.setText(String.format("%ddB",(setEq.get(7*(type-1)+1)-200)/10));
        delayBinding.tvChGain3.setText(String.format("%ddB",(setEq.get(7*(type-1)+2)-200)/10));
        delayBinding.tvChGain4.setText(String.format("%ddB",(setEq.get(7*(type-1)+3)-200)/10));
        delayBinding.tvChGain5.setText(String.format("%ddB",(setEq.get(7*(type-1)+4)-200)/10));
        delayBinding.tvChGain6.setText(String.format("%ddB",(setEq.get(7*(type-1)+5)-200)/10));
        delayBinding.tvChGain7.setText(String.format("%ddB",(setEq.get(7*(type-1)+6)-200)/10));

        delayBinding.trackSeekbar1.setProgress(setEq.get(7*(type-1)+0));
        delayBinding.trackSeekbar2.setProgress(setEq.get(7*(type-1)+1));
        delayBinding.trackSeekbar3.setProgress(setEq.get(7*(type-1)+2));
        delayBinding.trackSeekbar4.setProgress(setEq.get(7*(type-1)+3));
        delayBinding.trackSeekbar5.setProgress(setEq.get(7*(type-1)+4));
        delayBinding.trackSeekbar6.setProgress(setEq.get(7*(type-1)+5));
        delayBinding.trackSeekbar7.setProgress(setEq.get(7*(type-1)+6));



    }


    /**
     * 前左边
     */
    public  void highlowFrontLeft(View view){
        type=1;
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        updateView();
//        Gson gson=new Gson();
//        try {
//            Object people = gson.fromJson("[\n" +
//                    "    {\n" +
//                    "        \"user\": \"dd\",\n" +
//                    "        \"name\": \"dd\"\n" +
//                    "    }\n" +
//                    "]", new TypeToken<List<DemooBean.Data>>(){}.getType());
//            ArrayList<DemooBean.Data> datas= (ArrayList<DemooBean.Data>) people;
//            Log.e("HU","========"+datas.get(0).getUser());
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }



    /**
     * eq设置
     */
    public  void eqBottomSetting(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("CH"+view.getTag()+"频率设置");
        delayBinding.tvParmTitle.setTag(2);
        delayBinding.tvParm.setText(setQl.get(7*(type-1)+Integer.valueOf(view.getTag().toString())-1)+"");
        delayBinding.btnParmAdd.setTag(view.getTag());
        delayBinding.btnParmSub.setTag(view.getTag());
    }



    /**
     * 设置
     * 频率
     */
    public  void hzBottomSetting(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        delayBinding.tvParmTitle.setText("CH"+view.getTag()+"Q值");
        delayBinding.tvParmTitle.setTag(1);
        delayBinding.tvParm.setText(intPin(setHz.get(7*(type-1)+Integer.valueOf(view.getTag().toString())-1))+"");
        delayBinding.btnParmAdd.setTag(view.getTag());
        delayBinding.btnParmSub.setTag(view.getTag());
    }


    /**
     * 底部控制增加
     */
    public  void highlowBottomAdd(View view){
        int index=Integer.valueOf(7*(type-1)+Integer.valueOf(view.getTag().toString())-1);
        switch (delayBinding.tvParmTitle.getTag().toString()){
            case "1":
                int value=setHz.get(index);
                value=value>=2855?2855:(value+1);
                setHz.set(index,value);
                delayBinding.tvParm.setText(intPin(value)+"");
                switch (view.getTag().toString()){
                    case "1":
                        delayBinding.btnChFreq1.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "2":
                        delayBinding.btnChFreq2.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "3":
                        delayBinding.btnChFreq3.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "4":
                        delayBinding.btnChFreq4.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "5":
                        delayBinding.btnChFreq5.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "6":
                        delayBinding.btnChFreq6.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "7":
                        delayBinding.btnChFreq7.setText(String.format("%dHz",intPin(value)));
                        break;
                }
                break;
            case "2":
                double value1=setQl.get(index);
                value1=value1>=15?15:add(value1,0.1);
                Log.e("HU","=========value1=="+value1);
                setQl.set(index,value1);
                delayBinding.tvParm.setText(value1+"");
                switch (view.getTag().toString()){
                    case "1":
                        delayBinding.btnChQ1.setText(value1+"");
                        break;
                    case "2":
                        delayBinding.btnChQ2.setText(value1+"");
                        break;
                    case "3":
                        delayBinding.btnChQ3.setText(value1+"");
                        break;
                    case "4":
                        delayBinding.btnChQ4.setText(value1+"");
                        break;
                    case "5":
                        delayBinding.btnChQ5.setText(value1+"");
                        break;
                    case "6":
                        delayBinding.btnChQ6.setText(value1+"");
                        break;
                    case "7":
                        delayBinding.btnChQ7.setText(value1+"");
                        break;
                }
                break;
        }
    }


    /**
     * 递减
     */
    public  void highlowBottomSub(View view){
        int index=Integer.valueOf(7*(type-1)+Integer.valueOf(view.getTag().toString())-1);
        switch (delayBinding.tvParmTitle.getTag().toString()){
            case "1":
                int value=setHz.get(index);
                value=value<=0?2855:(value-1);
                setHz.set(index,value);
                delayBinding.tvParm.setText(intPin(value)+"");
                switch (view.getTag().toString()){
                    case "1":
                        delayBinding.btnChFreq1.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "2":
                        delayBinding.btnChFreq2.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "3":
                        delayBinding.btnChFreq3.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "4":
                        delayBinding.btnChFreq4.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "5":
                        delayBinding.btnChFreq5.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "6":
                        delayBinding.btnChFreq6.setText(String.format("%dHz",intPin(value)));
                        break;
                    case "7":
                        delayBinding.btnChFreq7.setText(String.format("%dHz",intPin(value)));
                        break;
                }
                break;
            case "2":
                double value1=setQl.get(index);
                value1=value1<=0.3?15:sub(value1,0.1);
                setQl.set(index,value1);
                delayBinding.tvParm.setText(value1+"");
                switch (view.getTag().toString()){
                    case "1":
                        delayBinding.btnChQ1.setText(value1+"");
                        break;
                    case "2":
                        delayBinding.btnChQ2.setText(value1+"");
                        break;
                    case "3":
                        delayBinding.btnChQ3.setText(value1+"");
                        break;
                    case "4":
                        delayBinding.btnChQ4.setText(value1+"");
                        break;
                    case "5":
                        delayBinding.btnChQ5.setText(value1+"");
                        break;
                    case "6":
                        delayBinding.btnChQ6.setText(value1+"");
                        break;
                    case "7":
                        delayBinding.btnChQ7.setText(value1+"");
                        break;
                }
                break;
        }
    }


    /**
     * 前右边
     */
    public  void highlowFrontRight(View view){
        type=3;
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        updateView();
    }

    /**
     * 后左边
     */
    public  void highlowAfterLeft(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        type=2;
        updateView();
    }

    /**
     * 后右边
     */
    public  void highlowAfterRight(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        type=4;
        updateView();
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 得到频率
     * @param value
     * @return
     */
    public int intPin(int value){
        int F;
        if(value<130){F=value+20;}
        else if(value>=130 && value<305){F=(value-130)*2+150;}
        else if(value>=305 && value<605){F=(value-305)*3+500;}
        else if(value>=605 && value<1255){F=(value-605)*4+1400;}
        else{F=(value-1255)*10+4000;}
        return F;
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
}
