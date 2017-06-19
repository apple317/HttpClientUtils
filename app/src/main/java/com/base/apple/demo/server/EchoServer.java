package com.base.apple.demo.server;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
/**
 * Created by apple_hsp on 17/5/18.
 */

public class EchoServer extends Service {
    private Toast toast;

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return echoBinder;
    }

    public EchoServerBinder echoBinder = new EchoServerBinder();

    public class EchoServerBinder extends Binder { //必须要实现一个Binder座位onBind的返回值
        public EchoServer getService() {
            return EchoServer.this;
        }
    }

}
