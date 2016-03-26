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
	Object get(String url,BaseParams params,HttpCallback callback);
	Object post(String url, BaseParams params, HttpCallback callback);
	Object post(String url, BaseParams params, HttpCallback callback,Object head,Object config);
	Object get(boolean shouldEncodeUrl,String url,BaseParams params,HttpCallback callback,Object head,Object config);
	Object post(Object tag,String url, BaseParams params, HttpCallback callback,Object head,Object config);

	/**
	 *
	 * @param tag 设置tag，用去批量取消或者其它用途
	 * @param shouldEncodeUrl
	 * @param url
	 * @param params
	 * @param callback
	 * @param head
	 * @param config
	 * @return
	 */
	Object get(Object tag,boolean shouldEncodeUrl,String url,BaseParams params,HttpCallback callback,Object head,Object config);

}
