package com.base.apple.demo.highlow.viewmodel;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.BR;
import com.base.apple.demo.HighlowBinding;
import com.base.apple.demo.R;
import com.base.apple.demo.main.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by kelin on 16-4-28.
 */
public class HighlowViewModel extends ViewModel {


    HighlowBinding delayBinding;

    /**
     * 1-6
     * 1:前左边 前左增益
     * 2:后左边 后左增益
     * 3:前右边 前右增益
     * 4:后右边 后右增益
     * 5:中置，顶中心
     * 6:底下中心 重低音增益
     */
    int type=0;


    ArrayList<Integer>  LowFrequency;

    ArrayList<Integer>  LowType;

    ArrayList<Integer>  LowRate;

    ArrayList<Integer>  HighFreguency;
    ArrayList<Integer> HighType;
    ArrayList<Integer>  HighRate;

    /**
     * -30 6 显示
     * 传数值0-36
     */
    ArrayList<Integer>  ClippingLevel;
    ArrayList<Integer>  ClippingResponseTime;
    /**
     * 释放时间
     * 最少10-20000
     */
    ArrayList<Integer>  ClippingReleaseTime;


    /**
     * 低通type
     * 0:Bworth
     * 1:Bessel
     * 2:Lnkwtz
     */
    ArrayList<String> filterType= new ArrayList<String>();

    /**
     * 斜率
     * 0:OFF
     * 1:12dB
     * 2:24dB
     */
    ArrayList<String> slope= new ArrayList<String>();


    //前车
    ArrayList<Integer> setBefore= new ArrayList<Integer>();
    //后车
    ArrayList<Integer> setAfter= new ArrayList<Integer>();
    //全车
    ArrayList<Integer> setFull= new ArrayList<Integer>();


    /**
     * 底部控制类型
     * 1:低通频率
     * 2:高通频率
     * 3:限幅电平
     * 4:限幅响应时间
     * 5:限幅关闭时间
     */
    int bottomType=0;




    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = act.getLayoutInflater().inflate(R.layout.fra_highlow, null);
        delayBinding = DataBindingUtil.bind(view);
        initData(savedInstanceState);
        return view;
    }


    @Override
    public void initData(Bundle bundle) {
        filterType.add("Bworth");
        filterType.add("Bessel");
        filterType.add("Lnkwtz");

        slope.add("OFF");
        slope.add("12dB");
        slope.add("24dB");

        delayBinding.setVariable(BR.highlow, this);
        LowFrequency=new ArrayList<Integer>();
        LowType=new ArrayList<Integer>();
        LowRate=new ArrayList<Integer>();
        HighFreguency=new ArrayList<Integer>();
        HighType=new ArrayList<Integer>();
        HighRate=new ArrayList<Integer>();
        ClippingLevel=new ArrayList<Integer>();
        ClippingResponseTime=new ArrayList<Integer>();
        ClippingReleaseTime=new ArrayList<Integer>();



        LowFrequency.add(2855);
        LowFrequency.add(2855);
        LowFrequency.add(2855);
        LowFrequency.add(2855);
        LowType.add(0);
        LowType.add(0);
        LowType.add(0);
        LowType.add(0);
        LowRate.add(0);
        LowRate.add(0);
        LowRate.add(0);
        LowRate.add(0);

        HighFreguency.add(0);
        HighFreguency.add(0);
        HighFreguency.add(0);
        HighFreguency.add(0);

        HighType.add(0);
        HighType.add(0);
        HighType.add(0);
        HighType.add(0);

        HighRate.add(0);
        HighRate.add(0);
        HighRate.add(0);
        HighRate.add(0);

        ClippingLevel.add(0);
        ClippingLevel.add(0);
        ClippingLevel.add(0);
        ClippingLevel.add(0);

        ClippingResponseTime.add(10);
        ClippingResponseTime.add(10);
        ClippingResponseTime.add(10);
        ClippingResponseTime.add(10);

        ClippingReleaseTime.add(10);
        ClippingReleaseTime.add(10);
        ClippingReleaseTime.add(10);
        ClippingReleaseTime.add(10);


        setBefore.add(2);
        setAfter.add(2);
        setFull.add(4);


        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);

        updateView();
    }


    MainActivity act;

    public HighlowViewModel(MainActivity activity) {
        this.act = activity;
    }


    @Override
    public void success(String content, BaseHttpClient client, Object parse) {
        super.success(content, client, parse);
        switch (client.getUrlIdentifier()) {
           // delayBinding.btnLbMs.setText(String.format("%.2fms",delayList.get(1)));
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
            case 0://前左
                delayBinding.tvFrontLeft.setSelected(true);
                break;
            case 2://后左
                delayBinding.tvAfterLeft.setSelected(true);
                break;
            case 1://前右
                delayBinding.tvFrontRight.setSelected(true);
                break;
            case 3://后右
                delayBinding.tvAfterRight.setSelected(true);
                break;
        }

        delayBinding.btnFilterLFreq.setText(String.format("%dHz",intPin(act.mainViewModel.CHhcut.get(type))));
        delayBinding.btnFilterLType.setText(filterType.get(act.mainViewModel.CHhcuttype.get(type))+"");
        delayBinding.btnFilterLRate.setText(slope.get(act.mainViewModel.CHHcutsope.get(type))+"");

        delayBinding.btnFilterHFreq.setText(String.format("%dHz",intPin(act.mainViewModel.CHLcut.get(type))));
        delayBinding.btnFilterHType.setText(filterType.get(act.mainViewModel.CHhcuttype.get(type))+"");
        delayBinding.btnFilterHRate.setText(slope.get(act.mainViewModel.CHLcutsope.get(type))+"");

        delayBinding.btnLimiterRange.setText(String.format("%ddB",ClippingLevel.get(type)-30));
        delayBinding.btnLimiterReplytime.setText(String.format("%dms",ClippingResponseTime.get(type)));
        delayBinding.btnLimiterReleasetime.setText(String.format("%dms",ClippingReleaseTime.get(type)));

    }




    /**
     * 低通频率
     */
    public  void LowpassFrq(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        bottomType=1;
        delayBinding.tvParmTitle.setText("低通频率设置");
        delayBinding.tvParm.setText(String.format("%dHz",intPin(act.mainViewModel.CHhcut.get(type))));
    }

    /**
     * 高通频率
     */
    public  void HighlowFrq(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        bottomType=2;
        delayBinding.tvParmTitle.setText("高通频率设置");
        delayBinding.tvParm.setText(String.format("%dHz",intPin(act.mainViewModel.CHLcut.get(type))));
    }


    /**
     * 限幅电平设置
     */
    public  void ClippingLevelFrq(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        bottomType=3;
        delayBinding.tvParmTitle.setText("限幅电平");
        delayBinding.tvParm.setText(String.format("%ddB",(ClippingLevel.get(type-1)-30)));
    }


    /**
     * 高通类型
     *
     */
    public  void HighlowType(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
       // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        builder.setItems(filterType.toArray(new String[filterType.size()]), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                act.mainViewModel.CHLcuttype.set(type,which);
                delayBinding.btnFilterHType.setText(filterType.get(act.mainViewModel.CHLcuttype.get(type))+"");
//                int afterType=setAfter.get(0);
//                int beforeType=setBefore.get(0);
//                int fullType=setFull.get(0);
//                if(fullType==4){
//                    if(afterType!=2){
//                        HighType.set(1,which);
//                        HighType.set(3,which);
//                    }
//                    if(beforeType!=2){
//                        HighType.set(0,which);
//                        HighType.set(2,which);
//                    }
//                }else{
//                    for(int i=0;i<4;i++){
//                        HighType.set(i,which);
//                    }
//                }
                //updateView();
            }
        });
        builder.show();
    }

    /**
     * 低通类型
     *
     */
    public  void LowpsssType(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        builder.setItems(filterType.toArray(new String[filterType.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                act.mainViewModel.CHhcuttype.set(type,which);
                delayBinding.btnFilterLType.setText(filterType.get(act.mainViewModel.CHhcuttype.get(type))+"");
//                int afterType=setAfter.get(0);
//                int beforeType=setBefore.get(0);
//                int fullType=setFull.get(0);
//                if(fullType==4){
//                    if(afterType!=2){
//                        LowType.set(1,which);
//                        LowType.set(3,which);
//                    }
//                    if(beforeType!=2){
//                        LowType.set(0,which);
//                        LowType.set(2,which);
//                    }
//                }else{
//                    for(int i=0;i<4;i++){
//                        LowType.set(i,which);
//                    }
//                }
               // updateView();
            }
        });
        builder.show();
    }

    /**
     * 高通斜率
     *
     */
    public  void HighlowSlope(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        builder.setItems(slope.toArray(new String[slope.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HighRate.set(type-1,which);
                delayBinding.btnFilterHRate.setText(slope.get(HighRate.get(type-1))+"");
                int afterType=setAfter.get(0);
                int beforeType=setBefore.get(0);
                int fullType=setFull.get(0);
                if(fullType==4){
                    if(afterType!=2){
                        HighRate.set(1,which);
                        HighRate.set(3,which);
                    }
                    if(beforeType!=2){
                        HighRate.set(0,which);
                        HighRate.set(2,which);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        HighRate.set(i,which);
                    }
                }
                updateView();

            }
        });
        builder.show();
    }

    /**
     * 低通斜率
     *
     */
    public  void LowpsssSlope(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        builder.setItems(slope.toArray(new String[slope.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LowRate.set(type-1,which);
                delayBinding.btnFilterLRate.setText(slope.get(LowRate.get(type-1))+"");
                int afterType=setAfter.get(0);
                int beforeType=setBefore.get(0);
                int fullType=setFull.get(0);
                if(fullType==4){
                    if(afterType!=2){
                        LowRate.set(1,which);
                        LowRate.set(3,which);
                    }
                    if(beforeType!=2){
                        LowRate.set(0,which);
                        LowRate.set(2,which);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        LowRate.set(i,which);
                    }
                }
                updateView();
            }
        });
        builder.show();
    }


    /**
     * 前场声设置
     *
     */
    public  void beforeSoundSetting(View view){
        delayBinding.checkboxFront.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("HU","onCheckedChanged==ischecked="+isChecked);
                delayBinding.checkboxFront.setChecked(setBefore.get(0)==2?false:true);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        String[] fullVal={"前左","前右","取消"};
        builder.setItems(fullVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setBefore.clear();
                setBefore.add(which);
                delayBinding.checkboxFront.setChecked(which==2?false:true);
                int setIndex=-1;
                int getIndex=-1;
                if(which==0){
                    setIndex=2;
                    getIndex=0;
                }else if(which==1){
                    setIndex=0;
                    getIndex=2;
                }
                if(setIndex>-1){
                    LowFrequency.set(setIndex,LowFrequency.get(getIndex));
                    LowType.set(setIndex,LowType.get(getIndex));
                    LowRate.set(setIndex,LowRate.get(getIndex));
                    HighFreguency.set(setIndex,HighFreguency.get(getIndex));
                    HighType.set(setIndex,HighType.get(getIndex));
                    HighRate.set(setIndex,HighRate.get(getIndex));
                    ClippingLevel.set(setIndex,ClippingLevel.get(getIndex));
                    ClippingResponseTime.set(setIndex,ClippingResponseTime.get(getIndex));
                    ClippingReleaseTime.set(setIndex,ClippingReleaseTime.get(getIndex));
                    updateView();
                }

            }
        });
        builder.show();
    }

    /**
     * 后场声设置
     *
     */
    public  void afterSoundSetting(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        String[] fullVal={"后左","后右","取消"};
        delayBinding.checkboxBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("HU","onCheckedChanged==ischecked="+isChecked);
                delayBinding.checkboxBack.setChecked(setBefore.get(0)==2?false:true);
            }
        });
        builder.setItems(fullVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAfter.clear();
                setAfter.add(which);
                delayBinding.checkboxBack.setChecked(which==2?false:true);
                int setIndex=-1;
                int getIndex=-1;
                if(which==0){
                    setIndex=3;
                    getIndex=1;
                }else if(which==1){
                    setIndex=1;
                    getIndex=3;
                }
                if(setIndex>-1){
                    LowFrequency.set(setIndex,LowFrequency.get(getIndex));
                    LowType.set(setIndex,LowType.get(getIndex));
                    LowRate.set(setIndex,LowRate.get(getIndex));
                    HighFreguency.set(setIndex,HighFreguency.get(getIndex));
                    HighType.set(setIndex,HighType.get(getIndex));
                    HighRate.set(setIndex,HighRate.get(getIndex));
                    ClippingLevel.set(setIndex,ClippingLevel.get(getIndex));
                    ClippingResponseTime.set(setIndex,ClippingResponseTime.get(getIndex));
                    ClippingReleaseTime.set(setIndex,ClippingReleaseTime.get(getIndex));
                    updateView();
                }
            }
        });
        builder.show();
    }

    /**
     * 全场设置
     *
     */
    public  void fullcourtSetting(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        String[] fullVal={"前左","前右","后左","后右","取消"};
        // builder.setTitle(_title);
        //    指定下拉列表的显示数据
        //    设置一个下拉的列表选择项
        delayBinding.checkboxMid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("HU","onCheckedChanged==ischecked="+isChecked);
                delayBinding.checkboxMid.setChecked(setBefore.get(0)==4?false:true);
            }
        });
        builder.setItems(fullVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setFull.clear();
                setFull.add(which);
                if(which==4){
                    setAfter.clear();
                    setAfter.add(2);
                    setBefore.clear();
                    setBefore.add(2);
                }
                delayBinding.checkboxMid.setChecked(which==4?false:true);
                delayBinding.checkboxBack.setChecked(which==4?delayBinding.checkboxBack.isChecked():false);
                delayBinding.checkboxFront.setChecked(which==4?delayBinding.checkboxFront.isChecked():false);
                delayBinding.checkboxBack.setEnabled(which==4?true:false);
                delayBinding.checkboxFront.setEnabled(which==4?true:false);
                if(which==4){
                    return;
                }
                for (int i=0;i<4;i++){
                    LowFrequency.set(i,LowFrequency.get(which));
                    LowType.set(i,LowType.get(which));
                    LowRate.set(i,LowRate.get(which));
                    HighFreguency.set(i,HighFreguency.get(which));
                    HighType.set(i,HighType.get(which));
                    HighRate.set(i,HighRate.get(which));
                    ClippingLevel.set(i,ClippingLevel.get(which));
                    ClippingResponseTime.set(i,ClippingResponseTime.get(which));
                    ClippingReleaseTime.set(i,ClippingReleaseTime.get(which));
                }
                updateView();
            }
        });
        builder.show();
    }







    /**
     * 限幅响应时间
     */
    public  void ClippingResponseTime(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        bottomType=4;
        delayBinding.tvParmTitle.setText("限幅响应时间");
        delayBinding.tvParm.setText(String.format("%dms",ClippingResponseTime.get(type-1)));
    }

    /**
     * 限幅释放时间
     */
    public  void ClippingCloseTime(View view){
        delayBinding.layoutTrackBottom.setVisibility(View.VISIBLE);
        bottomType=5;
        delayBinding.tvParmTitle.setText("限幅释放时间");
        delayBinding.tvParm.setText(String.format("%dms",ClippingReleaseTime.get(type-1)));
    }


    /**
     * 底部控制增加
     */
    public  void highlowBottomAdd(View view){
        int afterType=setAfter.get(0);
        int beforeType=setBefore.get(0);
        int fullType=setFull.get(0);
        switch (bottomType){
            case 1:
                int value=LowFrequency.get(type-1);
                value=value>=2855?2855:(value+1);
                LowFrequency.set(type-1,value);
                delayBinding.tvParm.setText(String.format("%dHz",intPin(LowFrequency.get(type-1))));
                delayBinding.btnFilterLFreq.setText(String.format("%dHz",intPin(LowFrequency.get(type-1))));
                if(fullType==4){
                    if(afterType!=2){
                        LowFrequency.set(1,value);
                        LowFrequency.set(3,value);
                    }
                    if(beforeType!=2){
                        LowFrequency.set(0,value);
                        LowFrequency.set(2,value);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        LowRate.set(i,value);
                    }
                }
                break;
            case 2:
                int value1=HighFreguency.get(type-1);
                value1=value1>=2855?2855:(value1+1);
                HighFreguency.set(type-1,value1);
                delayBinding.tvParm.setText(String.format("%dHz",intPin(value1)));
                delayBinding.btnFilterHFreq.setText(String.format("%dHz",intPin(value1)));
                if(fullType==4){
                    if(afterType!=2){
                        HighFreguency.set(1,value1);
                        HighFreguency.set(3,value1);
                    }
                    if(beforeType!=2){
                        HighFreguency.set(0,value1);
                        HighFreguency.set(2,value1);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        HighFreguency.set(i,value1);
                    }
                }
                break;
            case 3:
                int db=ClippingLevel.get(type-1);
                db=db>=36?36:(db+1);
                ClippingLevel.set(type-1,db);
                delayBinding.tvParm.setText(String.format("%ddB",(ClippingLevel.get(type-1)-30)));
                delayBinding.btnLimiterRange.setText(String.format("%ddB",(ClippingLevel.get(type-1)-30)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingLevel.set(1,db);
                        ClippingLevel.set(3,db);
                    }
                    if(beforeType!=2){
                        ClippingLevel.set(0,db);
                        ClippingLevel.set(2,db);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingLevel.set(i,db);
                    }
                }
                break;
            case 4:
                int response=ClippingResponseTime.get(type-1);
                response=response>=2000?2000:(response+10);
                ClippingResponseTime.set(type-1,response);
                delayBinding.btnLimiterReplytime.setText(String.format("%dms",ClippingResponseTime.get(type-1)));
                delayBinding.tvParm.setText(String.format("%dms",ClippingResponseTime.get(type-1)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingResponseTime.set(1,response);
                        ClippingResponseTime.set(3,response);
                    }
                    if(beforeType!=2){
                        ClippingResponseTime.set(0,response);
                        ClippingResponseTime.set(2,response);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingResponseTime.set(i,response);
                    }
                }
                break;
            case 5:
                int release=ClippingReleaseTime.get(type-1);
                release=release>=2000?2000:(release+10);
                ClippingReleaseTime.set(type-1,release);
                delayBinding.btnLimiterReleasetime.setText(String.format("%dms",ClippingReleaseTime.get(type-1)));
                delayBinding.tvParm.setText(String.format("%dms",ClippingReleaseTime.get(type-1)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingReleaseTime.set(1,release);
                        ClippingReleaseTime.set(3,release);
                    }
                    if(beforeType!=2){
                        ClippingReleaseTime.set(0,release);
                        ClippingReleaseTime.set(2,release);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingReleaseTime.set(i,release);
                    }
                }
                break;
        }
    }


    /**
     * 低通频率
     */
    public  void highlowBottomSub(View view){
        int afterType=setAfter.get(0);
        int beforeType=setBefore.get(0);
        int fullType=setFull.get(0);
        switch (bottomType){
            case 1:
                int value=LowFrequency.get(type-1);
                value=value<=0?0:(value-1);
                LowFrequency.set(type-1,value);
                delayBinding.tvParm.setText(String.format("%dHz",intPin(LowFrequency.get(type-1))));
                delayBinding.btnFilterLFreq.setText(String.format("%dHz",intPin(LowFrequency.get(type-1))));
                if(fullType==4){
                    if(afterType!=2){
                        LowFrequency.set(1,value);
                        LowFrequency.set(3,value);
                    }
                    if(beforeType!=2){
                        LowFrequency.set(0,value);
                        LowFrequency.set(2,value);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        LowRate.set(i,value);
                    }
                }
                break;
            case 2:
                int value1=HighFreguency.get(type-1);
                value1=value1<=0?0:(value1-1);
                HighFreguency.set(type-1,value1);
                delayBinding.tvParm.setText(String.format("%dHz",intPin(value1)));
                delayBinding.btnFilterHFreq.setText(String.format("%dHz",intPin(value1)));
                if(fullType==4){
                    if(afterType!=2){
                        HighFreguency.set(1,value1);
                        HighFreguency.set(3,value1);
                    }
                    if(beforeType!=2){
                        HighFreguency.set(0,value1);
                        HighFreguency.set(2,value1);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        HighFreguency.set(i,value1);
                    }
                }
                break;
            case 3:
                int db=ClippingLevel.get(type-1);
                db=db<=0?0:(db-1);
                ClippingLevel.set(type-1,db);
                delayBinding.tvParm.setText(String.format("%ddB",(ClippingLevel.get(type-1)-30)));
                delayBinding.btnLimiterRange.setText(String.format("%ddB",(ClippingLevel.get(type-1)-30)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingLevel.set(1,db);
                        ClippingLevel.set(3,db);
                    }
                    if(beforeType!=2){
                        ClippingLevel.set(0,db);
                        ClippingLevel.set(2,db);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingLevel.set(i,db);
                    }
                }
                break;
            case 4:
                int response=ClippingResponseTime.get(type-1);
                response=response<=10?10:(response-10);
                ClippingResponseTime.set(type-1,response);
                delayBinding.btnLimiterReplytime.setText(String.format("%dms",ClippingResponseTime.get(type-1)));
                delayBinding.tvParm.setText(String.format("%dms",ClippingResponseTime.get(type-1)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingResponseTime.set(1,response);
                        ClippingResponseTime.set(3,response);
                    }
                    if(beforeType!=2){
                        ClippingResponseTime.set(0,response);
                        ClippingResponseTime.set(2,response);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingResponseTime.set(i,response);
                    }
                }
                break;
            case 5:
                int release=ClippingReleaseTime.get(type-1);
                release=release<=10?10:(release-10);
                ClippingReleaseTime.set(type-1,release);
                delayBinding.btnLimiterReleasetime.setText(String.format("%dms",ClippingReleaseTime.get(type-1)));
                delayBinding.tvParm.setText(String.format("%dms",ClippingReleaseTime.get(type-1)));
                if(fullType==4){
                    if(afterType!=2){
                        ClippingReleaseTime.set(1,release);
                        ClippingReleaseTime.set(3,release);
                    }
                    if(beforeType!=2){
                        ClippingReleaseTime.set(0,release);
                        ClippingReleaseTime.set(2,release);
                    }
                }else{
                    for(int i=0;i<4;i++){
                        ClippingReleaseTime.set(i,release);
                    }
                }
                break;
        }
    }


    /**
     * 前左边
     */
    public  void highlowFrontLeft(View view){
        type=0;
        delayBinding.layoutTrackBottom.setVisibility(View.INVISIBLE);
        updateView();
    }

    /**
     * 前右边
     */
    public  void highlowFrontRight(View view){
        type=1;
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
        type=3;
        updateView();
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
}
