package com.base.apple.demo.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.base.apple.demo.R;
import com.base.apple.demo.common.AppBaseActivity;
import com.base.apple.demo.main.viewmodel.MainViewModel;


public class MainActivity extends AppBaseActivity {


    MainViewModel mainViewModel;

    @Override
    public void initView(Bundle bundle) {
        super.initView(bundle);
        mainViewModel=new MainViewModel(this);
        mainViewModel.initView(null,null,bundle);
    }





    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
        mainViewModel.initData(bundle);
    }


//    /**
//     * 替换fragment
//     */
//    private void replaceContent(Fragment fragment, String tabId) {
//        try {
//            FragmentTransaction transaction = ()
//                    .beginTransaction();
//            transaction.add(R.id.lay_content, fragment, tabId);
//            transaction.commitAllowingStateLoss();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }





}
