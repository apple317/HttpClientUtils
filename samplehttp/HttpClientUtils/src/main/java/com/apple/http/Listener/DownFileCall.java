package com.apple.http.Listener;

import com.apple.http.utils.MD5Util;
import com.apple.http.utils.StorageUtils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 *
 @author 胡少平
 */
public class DownFileCall implements Callback{

    //httpcallback是自定义的请求返回对象
    HttpCallback callBack;
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

    public DownFileCall(Context context, HttpCallback response, String requestUrl, String destFileDir, String destFileName) {
        this.callBack = response;
        url = requestUrl;
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        mContext=context;
    }

    /**
     * Called when the request could not be executed due to cancellation, a
     * connectivity problem or timeout. Because networks can fail during an
     * exchange, it is possible that the remote server accepted the request
     * before the failure.
     */
    @Override
    public void onFailure(Call call, IOException e) {
        Log.i("HU", "handle==onFailure=REQ_GET_INIT=reqType=");

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
    public void onResponse(Call call, Response response)  throws IOException {
        try {
            Log.i("HU","======onResponse==file=");
            if (response.isSuccessful()) {
                //成功得到文本信息
                 saveFile(response);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 文件保存下载
     * @param response
     * @return
     * @throws IOException
     */
    public File saveFile(Response response) throws IOException
    {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try
        {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            File dir;
            //+"/"+mContext.getPackageName()
            if(destFileDir==null||destFileDir.trim().toString().equals("")){
                destFileDir=StorageUtils.getCacheDirectory(mContext).getAbsolutePath();
                dir = new File(destFileDir);
            }else{
                dir = new File(destFileDir);
            }
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            if(destFileName==null||destFileName.trim().toString().equals("")) {
                destFileName=MD5Util.md5(url);
            }
            File file = new File(dir, destFileName);
            Log.i("HU","======bytes==file="+file.getPath());
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1)
            {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                Log.i("HU","======bytes==="+finalSum);
                callBack.onProgress(finalSum, total, sum == -1);
            }
            fos.flush();
            return file;

        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (IOException e)
            {
            }
            try
            {
                if (fos != null) fos.close();
            } catch (IOException e)
            {
            }

        }
    }
}
