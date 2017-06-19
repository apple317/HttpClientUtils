package com.base.apple.demo.api.widget.loadview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jucaicat.market.LoadViewBinding;
import com.jucaicat.market.R;

/**
 * Created by zhangliang on 2017/3/27. 数据加载
 */

public class LoadView extends LinearLayout {
    private LoadViewBinding loadViewBinding;

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        loadViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.load_view, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams( LayoutParams.MATCH_PARENT,  LayoutParams.MATCH_PARENT);
        setData(true);
        addView(loadViewBinding.getRoot(),layoutParams);
    }


    public void setData(boolean isShow) {
        loadViewBinding.setIsNotData(isShow);
    }
}
