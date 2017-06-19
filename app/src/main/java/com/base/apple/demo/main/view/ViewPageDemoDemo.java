package com.base.apple.demo.main.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.base.apple.demo.delay.view.DelayFragement;
import com.base.apple.demo.eq.view.EqFragement;
import com.base.apple.demo.highlow.view.HighlowFragement;
import com.base.apple.demo.sound.view.SoundFragement;
import com.base.apple.demo.system.view.SystemFragement;

import java.util.ArrayList;


public class ViewPageDemoDemo extends ViewPager {

    public ViewPageDemoDemo(Context context) {
        super(context);
    }

    public ViewPageDemoDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("HU","ViewPageDemoDemo==onTouchEvent="+ev.getAction());
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("HU","ViewPageDemoDemo==onInterceptTouchEvent="+ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("HU","ViewPageDemoDemo==dispatchTouchEvent="+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }



}
