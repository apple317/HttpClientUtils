package com.base.apple.demo.main.viewmodel;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.base.apple.demo.MainBinding;
import com.base.apple.demo.R;
import com.base.apple.demo.main.view.MainActivity;
import com.base.apple.demo.main.view.TabAdapter;
import  android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
/**
 * Created by kelin on 16-4-28.
 */
public class MainViewModel extends ViewModel implements View.OnClickListener {
    Socket client;
    public OutputStream outputStream=null;
    public InputStream inputStream=null;
    StringBuilder indata,panduan;


    MainBinding mainActivityBinding;
    private Toast toast=null;
    public boolean isConnected,B;byte[]Sendbutter;
    byte[] Revicebuffer = new byte[1024];int readSize;
    /****************************************/
    public int CHnum=0;//通道码（前左=0；前右=1；后左=2；后右=3）
    public ArrayList<Integer> CHgain;//通道增益
    public ArrayList<Integer> CHphase;//通道相位
    public ArrayList<Integer> CHmute;//通道静音
    public ArrayList<Integer> CHhcut;public ArrayList<Integer> CHhcuttype;public ArrayList<Integer> CHHcutsope;//通道高切、类型、斜率
    public ArrayList<Integer> CHLcut;public ArrayList<Integer> CHLcuttype;public ArrayList<Integer> CHLcutsope;//通道低切、类型、斜率
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dev_connect:
                Log.i("1", "onClick: ");
                showToast("你好!");
                isConnected=false;
                Sendbutter=new byte[1];Sendbutter[0]=(byte)0x4B;B=false;
                wificonnect();
                break;

        }
    }
    public void wificonnect(){

        new Thread(){   //新建线程进行网络连接
            public void run(){
                try
                {
                    String serveraddr = "192.168.1.254";// TCPServer.SERVERIP
                    int port=8080;
                    SocketAddress my_sockaddr = new InetSocketAddress(serveraddr, port);
                    client = new Socket();
                    client.connect(my_sockaddr,2000);

                    outputStream = client.getOutputStream();
                    inputStream = client.getInputStream();
                    outputStream.write(Sendbutter);
                    outputStream.flush();
                    Log.d("isConnected0", isConnected+"");
                    new Thread(new TCPServerThread(handler)).start();
                }

                catch (NumberFormatException e)
                {
                    // TODO Auto-generated catch block
                    Log.d("TAG", e.getMessage());
                    showToast("网络连接错误!");
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    Log.d("TAG", e.getMessage());
                    showToast("网络连接错误!");
                }
            }
        }.start();
    }
    public class TCPServerThread extends Thread
    {
        Handler handler;
        public TCPServerThread(Handler h)
        {
            handler = h;
        }
        public void run()
        {
            //tvRecv.setText("start");
            byte[] content=null;
            Log.d("Revicebuffer", Revicebuffer+"");
            while (true)
            {
                try
                {

                    readSize = inputStream.read(Revicebuffer);
                    if(readSize<0){
                        showToast("与服务器连接失败!");
                        return;
                    }
                    if((content=Revicebuffer)!=null){
                        Message msg=new Message();
                        msg.what=0x1;
                        msg.obj=content;
                        handler.sendMessage(msg);//发送消息
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

        }
    };
    //将获取的int转为真正的ip地址,参考的网上的，修改了下
    private String intToIp(int i){
        return (i & 0xFF)+ "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"."+((i >> 24 ) & 0xFF );
    }
    /**
     * toast提示
     *
     * @param text
     *            -提示内容
     * @author Junhui
     */
    public void showToast(String text) {
        //Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        if(toast == null)
        {
            toast = Toast.makeText(act, text, Toast.LENGTH_SHORT);
        }
        else
        {
            toast.setText(text);
        }
        toast.show();
    }
    /*
       发送数据
     */
    public void Senddata(byte[]data){
        showToast("发送数据");
        if(isConnected==true){
            try {
                outputStream.write(data);outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initData(Bundle bundle) {
        mainActivityBinding.idViewpage.setAdapter(
                new TabAdapter(act.getSupportFragmentManager()));
        mainActivityBinding.idIndicator.setViewPager(mainActivityBinding.idViewpage);
        mainActivityBinding.tvTitlebar.setText("声道设置");
        mainActivityBinding.btnDevConnect.setOnClickListener(this);//监听
        mainActivityBinding.idViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mainActivityBinding.tvTitlebar.setText("声道设置1");

                        break;
                    case 1:
                        mainActivityBinding.tvTitlebar.setText("高低通滤波器1");
                        break;
                    case 2:
                        mainActivityBinding.tvTitlebar.setText("EQ设置");
                        break;
                    case 3:
                        mainActivityBinding.tvTitlebar.setText("延时设置");
                        break;
                    case 4:
                        mainActivityBinding.tvTitlebar.setText("系统设置");
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    MainActivity act;

    public MainViewModel(MainActivity activity) {
        this.act = activity;
        CHgain=new ArrayList<Integer>();CHphase=new ArrayList<Integer>();CHmute=new ArrayList<Integer>();
        CHhcut=new ArrayList<Integer>();CHhcuttype=new ArrayList<Integer>();CHHcutsope=new ArrayList<Integer>();
        CHLcut=new ArrayList<Integer>();CHLcuttype=new ArrayList<Integer>();CHLcutsope=new ArrayList<Integer>();

        CHgain.add(30);CHgain.add(40);CHgain.add(50);CHgain.add(60);
        CHphase.add(0);CHphase.add(0);CHphase.add(127);CHphase.add(127);
        CHmute.add(0);CHmute.add(0);CHmute.add(127);CHmute.add(127);
//        //高切
        CHhcut.add(2855);CHhcut.add(2855);CHhcut.add(2855);CHhcut.add(2855);
        CHhcuttype.add(0);CHhcuttype.add(0);CHhcuttype.add(0);CHhcuttype.add(0);
        CHHcutsope.add(0);CHHcutsope.add(0);CHHcutsope.add(0);CHHcutsope.add(0);
        //低切
        CHLcut.add(0);CHLcut.add(0);CHLcut.add(0);CHLcut.add(0);
        CHLcuttype.add(0);CHLcuttype.add(0);CHLcuttype.add(0);CHLcuttype.add(0);
        CHLcutsope.add(0);CHLcutsope.add(0);CHLcutsope.add(0);CHLcutsope.add(0);
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
