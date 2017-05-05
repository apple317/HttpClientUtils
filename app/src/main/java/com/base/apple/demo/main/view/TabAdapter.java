package com.base.apple.demo.main.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base.apple.demo.delay.view.DelayFragement;
import com.base.apple.demo.eq.view.EqFragement;
import com.base.apple.demo.highlow.view.HighlowFragement;
import com.base.apple.demo.sound.view.SoundFragement;
import com.base.apple.demo.system.view.SystemFragement;

import java.util.ArrayList;


public class TabAdapter extends FragmentPagerAdapter {

    //标题确定有多少个Fragment
    public static String[] TITLES = new String[]{"声道", "高低通", "EQ", "延时", "系统"};
    private Fragment soudFra, highlow, eqFra, dedayFra, sysFra;
    ArrayList<Fragment> arrayList = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
        arrayList.add(new SoundFragement());
        arrayList.add(new HighlowFragement());
        arrayList.add(new EqFragement());
        arrayList.add(new DelayFragement());
        arrayList.add(new SystemFragement());
    }

    @Override
    public Fragment getItem(int arg0) {
        return arrayList.get(arg0);
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

}
