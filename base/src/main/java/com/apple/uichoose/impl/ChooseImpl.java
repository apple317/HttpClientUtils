package com.apple.uichoose.impl;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import com.apple.uichoose.common.BaseFilter;
import com.apple.uichoose.common.ChooseClient;
import com.apple.uichoose.common.ChooseConf;
import com.apple.uichoose.listener.ChooseListener;

import java.util.Iterator;
import java.util.Set;

/**
 * @author 胡少平
 */
public class ChooseImpl {

    //单例模式实现
    public static ChooseImpl instance;

    public static ChooseImpl getChooseImpl() {
        if (instance == null) {
            synchronized (ChooseImpl.class) {
                if (instance == null) {
                    instance = new ChooseImpl();
                }
            }
        }
        return instance;
    }


    public ChooseImpl() {

    }


    /**
     * 路由核心跳转逻辑
     */
    public void openUrl(BaseFilter baseFilter, ChooseConf chooseConf, ChooseClient chooseClient) {
        if (baseFilter != null) {
            if (baseFilter.executeFilter(chooseClient.getUrl())) {
                if (baseFilter.actionClass != null) {
                    Intent intent = new Intent();
                    intent.setClass(chooseConf.getContext(), baseFilter.actionClass);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("choose_data", chooseClient.getParamData());
                    chooseConf.getContext().startActivity(intent);
                }
            }
        }
    }


}
