package com.base.apple.demo.main.viewmodel;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.MainBinding;
import com.base.apple.demo.R;
import com.base.apple.demo.main.view.MainActivity;
import com.base.apple.demo.main.view.TabAdapter;



/**
 * Created by kelin on 16-4-28.
 */
public class MainViewModel extends ViewModel implements View.OnClickListener {



    MainBinding mainActivityBinding;




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }




    @Override
    public void initData(Bundle bundle) {
        mainActivityBinding.idViewpage.setAdapter(
                new TabAdapter(act.getSupportFragmentManager()));
        mainActivityBinding.idIndicator.setViewPager(mainActivityBinding.idViewpage);
    }


    MainActivity act;

    public MainViewModel(MainActivity activity) {
        this.act = activity;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivityBinding = DataBindingUtil.setContentView(act, R.layout.main);
        return mainActivityBinding.getRoot();
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
}
