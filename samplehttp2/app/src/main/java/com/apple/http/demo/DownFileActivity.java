package com.apple.http.demo;

import com.apple.http.common.BaseHttpClient;
import com.apple.http.entity.DownEntity;
import com.apple.http.entity.METHOD;
import com.apple.http.listener.DownCallback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * post 上传文件
 *
 * @author hushaoping
 */
public class DownFileActivity extends AppCompatActivity  {
    ProgressBar id_progress;
    TextView txt_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        id_progress = (ProgressBar) findViewById(R.id.id_progress);
        txt_content = (TextView) findViewById(R.id.txt_content);

    }

    /**
     * addUrl 添加url地址
     * put传入参数可以
     *setTag 设置tag
     * downName 下载文件命名
     * downDir 下载文件目录 如果不传会默认生成一个getCacheDirectory
     * DownEntity 下载返回数据实体：
     * public String name;//名称
     public String path;//保存地址
     public String url;
     public int httpCode;//http网络状态
     //下载是否完成
     public boolean statue;//下载是否完成
     public long currentByte;//下载当前字节数
     public long totalByte;//下载总字节数
     public boolean isExecuted;//下载是否执行
     public String dir;//下载目录

     public boolean isCanceled;//下载是否取消
     public String message;//下载返回消息
     * @param view
     */
    public void downData(View view){
        /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().newBuilder().url("http://112.65.235.160/vlive.qqvideo.tc.qq.com/m0019469p4a.mp4?vkey=4C7F305D62ABA38AF8BF474C40A0DF9700C8F07BF29BE26D76F17F8A7E73B9FEB1424CC479C4C863BFBDD095AA5EBE49A0CDE3EAEB32E2AD0C009E7C5B37521C0912AF6905C70C601471E664777B9C38C726B03E8D193D62&br=34&platform=2&fmt=msd&sdtfrom=v3010&type=mp4&locid=89489e75-bb18-40e4-989b-89d6b34adf32&size=56306437&ocid=1362567084")
                .downName("apple_nba").method(METHOD.DOWNLOAD_FILE).build().execute(new DownCallback() {
            @Override
            public void downProgress(DownEntity entity) {
                Message msg = new Message();
                msg.obj = entity;
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    try{
                        DownEntity entity=(DownEntity)msg.obj;
                        Log.i("HU", "======bytes===" + entity.currentByte + "==contenLength==" + entity.totalByte);
                        id_progress.setProgress((int) (100 * (float) entity.currentByte * 1.0f / entity.totalByte));
                        txt_content.setText("当前下载的文件目录是"+entity.path+"文件名称:"+entity.name+"网络返回code"+entity.httpCode+"===服务端返回消息=="+entity.message);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    };

}
