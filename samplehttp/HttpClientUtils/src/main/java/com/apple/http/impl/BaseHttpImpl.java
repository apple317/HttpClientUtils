package com.apple.http.impl;


import com.apple.http.Listener.HttpCallback;
import com.apple.http.common.BaseParams;

import android.content.Context;


/**
 * 请求对象模板接口
 * BaseHttpClient such is the definition of a common network parameters into the model,
 * all network expansion interface to implement the first
 * interface to define the request, and can be customized to achieve a new interface
 @author 胡少平
 */
public interface BaseHttpImpl {
	/**
	 * 网络库接口定义
	 */
	Object get(String url,BaseParams params,HttpCallback callback);
	Object downloadFile(Context context,String url,HttpCallback callback,BaseParams params,String destFileDir, String destFileNam);

	Object get(boolean shouldEncodeUrl,String url,BaseParams params,HttpCallback callback,Object head,Object config);

	Object post(String url, BaseParams params, HttpCallback callback);
	Object post(String url, BaseParams params, HttpCallback callback,String mediatype);
	Object post(String url, BaseParams params, HttpCallback callback,String mediatype,Object head);

}
