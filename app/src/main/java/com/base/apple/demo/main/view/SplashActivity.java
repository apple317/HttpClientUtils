package com.base.apple.demo.main.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.base.apple.demo.R;
import com.base.apple.demo.common.AppBaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangliang on 16/4/7. 新版启动页
 */
public class SplashActivity extends AppBaseActivity {


    ViewPageDemoDemo mViewPageMainContent;
    List<View> advPics;
    ListView listView;
    @Override
    public void initView(Bundle bundle) {
        super.initView(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        advPics=new ArrayList<>();
        for(int i=0;i<10;i++){
            View view=getLayoutInflater().inflate(R.layout.splash_item,null);
            view.findViewById(R.id.btn_ddddd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            view.findViewById(R.id.btn_ddddd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("HU","splash==onclick=dddd=");

                }
            });
            view.findViewById(R.id.iv_splash).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("HU","splash==onclick=iv=");

                }
            });
//            view.findViewById(R.id.tv_fin).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.e("HU","splash==onclick=fin=");
//
//                }
//            });


            advPics.add(view);
        }
        listView=(ListView)findViewById(R.id.listview);
        mViewPageMainContent=new ViewPageDemoDemo(getApplication());
        mViewPageMainContent.setAdapter(new ViewPagerontentAdapter(advPics));

        listView.addView(mViewPageMainContent);
        //  handler.postDelayed(runnable, 3000); // 开始Timer

    }


    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    };


    class ViewPagerontentAdapter extends PagerAdapter {

        List<View> advPics;
        public ViewPagerontentAdapter( List<View> advPics) {
            super();
            this.advPics=advPics;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(advPics.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(advPics.get(position), 0);
            return advPics.get(position);
        }




    }





    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
    }

}
