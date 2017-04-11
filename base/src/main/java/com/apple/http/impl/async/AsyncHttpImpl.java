package com.apple.http.impl.async;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 *
 * @author 胡少平
 *
 */
//public class AsyncHttpClientImpl implements BaseHttpClient {
//
//	private AsyncHttpClient client=null;
//
//	//单例模式实现
//	private static AsyncHttpClientImpl instance;
//
//	public static AsyncHttpClientImpl getHupuHttpClient() {
//		if (instance == null)
//			instance = new AsyncHttpClientImpl();
//		return instance;
//	}
//
//	private AsyncHttpClientImpl() {
//		client = new AsyncHttpClient();
//	}
//
//	@Override
//	public void get(String reqType, String url, RequestParams cacheParams,
//			HttpCallback callback) {
//		client.get(null, url, cacheParams, new BaseHttpHandler(callback, reqType));
//	}
//
//	@Override
//	public void get(String reqType, Context context, String url,
//			RequestParams cacheParams, HttpCallback callback) {
//		// TODO Auto-generated method stub
//		client.get(context, url, cacheParams, new BaseHttpHandler(callback,reqType));
//	}
//
//	@Override
//	public void post(String reqType, Context context, String url,
//			RequestParams params, HttpCallback callback) {
//		// TODO Auto-generated method stub
//		client.post(context,url,params,new BaseHttpHandler(callback,reqType));
//	}
//
//	@Override
//	public void post(String reqType, Context context, String url, HttpEntity entity, String contentType, HttpCallback callback) {
//
//	}
//
//
//	@Override
//	public void get(Context context, BaseParams params, HttpCallback callback) {
//
//	}
//
//	@Override
//	public void post(Context context, BaseParams params, HttpCallback callback) {
//
//	}
//
//	@Override
//	public void post(String reqType, Context context, String url,
//			Header[] headers, HttpEntity entity, String contentType,
//			HttpCallback callback) {
//		// TODO Auto-generated method stub
//		client.post(context,url,headers,entity,null,new BaseHttpHandler(callback,reqType));
//	}
//
//	public  void clearRequest(){
//		client.cancelAllRequests(true);
//	}
//
//}
