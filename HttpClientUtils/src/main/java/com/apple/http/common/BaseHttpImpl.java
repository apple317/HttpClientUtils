package com.apple.http.common;




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
	void get(String url,BaseParams params,HttpCallback callback);
	void post(String url, BaseParams params, HttpCallback callback);
	void post(String url, BaseParams params, HttpCallback callback,Object head,Object config);
	void get(boolean shouldEncodeUrl,String url,BaseParams params,HttpCallback callback,Object head,Object config);

}
