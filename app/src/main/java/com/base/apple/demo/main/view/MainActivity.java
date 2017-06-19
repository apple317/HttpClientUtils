package com.base.apple.demo.main.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.base.apple.demo.R;
import com.base.apple.demo.common.AppBaseActivity;
import com.base.apple.demo.main.viewmodel.MainViewModel;
import com.base.apple.demo.server.EchoServer;


public class MainActivity extends AppBaseActivity {


   public MainViewModel mainViewModel;
    EchoServer echoServer;
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
        Intent intent = new Intent(this, EchoServer.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
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


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            echoServer=((EchoServer.EchoServerBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };



}
