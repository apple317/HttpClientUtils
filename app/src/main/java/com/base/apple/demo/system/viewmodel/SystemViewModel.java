package com.base.apple.demo.system.viewmodel;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.R;



/**
 * Created by kelin on 16-4-28.
 */
public class SystemViewModel extends ViewModel implements View.OnClickListener {




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }




    @Override
    public void initData(Bundle bundle) {
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return act.getLayoutInflater().inflate(R.layout.fra_setting,null);
    }


    Activity act;

    public SystemViewModel(Activity activity) {
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
}
