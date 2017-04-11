package com.apple.http.listener.okhttp;

import android.content.Context;


import com.apple.http.entity.DownEntity;
import com.apple.http.entity.DownType;
import com.apple.http.listener.BaseCallback;
import com.apple.http.utils.StorageUtils;
import com.apple.tool.MachineInfo;
import com.apple.http.common.BaseHttpClient;
import com.apple.tool.MD5Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * @author 胡少平
 */
public class DownFileCall implements Callback {

    //httpcallback是自定义的请求返回对象
    BaseCallback callBack;
    //url是请求地址
    String url;
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;
    Context mContext;

    DownEntity mDownEntity;

    BaseHttpClient baseHttpClient;



    public DownFileCall(BaseHttpClient baseHttpClient, BaseCallback response, String requestUrl, String destFileDir, String destFileName) {
        this.callBack = response;
        this.url = requestUrl;
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        this.mContext = baseHttpClient.getConfiguration().getContext();
        this.baseHttpClient=baseHttpClient;
        mDownEntity = new DownEntity();
        mDownEntity.downUrl(url);
        if (destFileDir!=null&&destFileDir.trim().toString().equals(""))
            mDownEntity.downDir(destFileDir);
        if (destFileName!=null&&destFileName.trim().toString().equals(""))
            mDownEntity.downName(destFileName);
    }



    public Context getContext() {
        return mContext;
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     */
    @Override
    public void onFailure(Call call, IOException e) {
        mDownEntity.statue = DownType.DOWNLOAD_ERROR;
        mDownEntity.isCanceled = call.isCanceled();
        mDownEntity.isExecuted = call.isExecuted();
        mDownEntity.downMessage(e.toString());
        if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
            baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));
    }

    /**
     * Called when the HTTP response was successfully returned by the remote
     * server. The callback may proceed to read the response body with {@link
     * Response#body}. The response is still live until its response body is
     * closed with {@code response.body().close()}. The recipient of the callback
     * may even consume the response body on another thread.
     *
     * <p>Note that transport-layer success (receiving a HTTP response code,
     * headers and body) does not necessarily indicate application-layer
     * success: {@code response} may still indicate an unhappy HTTP response
     * code like 404 or 500.
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            if (response.isSuccessful()) {
                //成功得到文本信息
                mDownEntity.downCode(response.code());
                mDownEntity.downMessage(response.body().toString());
                saveFile(response);
            } else {
                mDownEntity.downStatue(DownType.DOWNLOAD_ERROR);
                mDownEntity.downCode(response.code());
                mDownEntity.downMessage(response.body().toString());
                if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
                    baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            mDownEntity.downStatue(DownType.DOWNLOAD_ERROR);
            mDownEntity.downCode(response.code());
            mDownEntity.downMessage(response.body().toString());
            if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
                baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));
        }
    }

    /**
     * 文件保存下载
     */
    public File saveFile(Response response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dir;
                    long sdcardAvailable = MachineInfo
                                      .getAvailableExternalMemorySize();
                   if (sdcardAvailable < total) {
                       mDownEntity.downStatue(DownType.DOWNLOAD_SPACE);
                           if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
                                       baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));
                              return null;
                           }

            if (destFileDir == null || destFileDir.trim().toString().equals("")) {
                destFileDir = StorageUtils.getCacheDirectory(mContext).getAbsolutePath();
                dir = new File(destFileDir);
            } else {
                dir = new File(destFileDir);
            }
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (destFileName == null || destFileName.trim().toString().equals("")) {
                destFileName = MD5Util.md5(url)+"."+resolve(url);
            }
            File file = new File(dir, destFileName);
            mDownEntity.downPath(file.getPath());
            mDownEntity.downName(destFileName);
            fos = new FileOutputStream(file);
                    long time=System.currentTimeMillis();

            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                mDownEntity.currentByte(finalSum);
                mDownEntity.totalByte(total);
                         mDownEntity.downStatue(DownType.DOWNLOAD_GOING);
                        long current=System.currentTimeMillis();
                             if((current-time)>400){
                                      time=current;
                                    if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
                                            baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));
                                 }
            }
            fos.flush();
                      mDownEntity.downStatue(DownType.DOWNLOAD_COMPLETE);
                   if(baseHttpClient!=null&&baseHttpClient.configuration!=null&&baseHttpClient.configuration.getHandler()!=null)
                              baseHttpClient.configuration.getHandler().post(new LoadTask(mDownEntity));

            return file;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }

        }
    }

    public static String resolve(String downHttpUrl) {
        // TODO Auto-generated method stub
        String httpUrl="mp4";
        String[] downArray=downHttpUrl.split(".");
        int length=downArray.length;
        if(length>0){
            httpUrl=downArray[length-1];
        }
        return httpUrl;
    }

    final class LoadTask implements Runnable{
        private final DownEntity mDownEntity;
        public LoadTask(DownEntity mDownEntity) {
            this.mDownEntity = mDownEntity;
        }
        @Override
        public void run() {
            callBack.downProgress(mDownEntity);
        }
    }

}
