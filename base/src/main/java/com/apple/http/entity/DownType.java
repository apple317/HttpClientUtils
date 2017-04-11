package com.apple.http.entity;

/**
 * Created by apple_hsp on 16/3/31.
 * 定义请求访问方式
 */
public class DownType {
    //开始下载
    public static final int DOWNLOAD_START =0;
    //正在下载
    public static final int DOWNLOAD_GOING=1;
    //下载完成
    public static final int DOWNLOAD_COMPLETE = 2;
    //空间不足
    public static final int DOWNLOAD_SPACE =3;
    //下载错误
    public static final int DOWNLOAD_ERROR =4;

}
