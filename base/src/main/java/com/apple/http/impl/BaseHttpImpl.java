package com.apple.http.impl;

import com.apple.http.common.HttpConfiguration;
import com.apple.http.listener.BaseCallback;
import com.apple.http.common.BaseHttpClient;


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
	 *   public static final String GET = "GET";
	 public static final String POST_STRING = "POST_STRING";
	 public static final String POST_FORM = "POST_FORM";
	 public static final String POST_FILE = "POST_FILE";
	 public static final String DOWNLOAD_FILE = "DOWN_FILE";
	 public static final String POST_FILE_PROGRESS = "POST_FILE_PROGRESS";
	 public static final String POST_FORM_PROGRESS = "POST_FORM_PROGRESS";
	 public static final String PUT = "PUT";
	 public static final String PATCH = "PATCH";
	 public static final String DELETE = "DELETE";
	 */
	void get(BaseHttpClient client, BaseCallback callback);
	void put(BaseHttpClient client, BaseCallback callback);
	void patch(BaseHttpClient client, BaseCallback callback);
	void delete(BaseHttpClient client, BaseCallback callback);

	void postString(BaseHttpClient client, BaseCallback callback);
	void postFile(BaseHttpClient client, BaseCallback callback);
	void postForm(BaseHttpClient client, BaseCallback callback);
	void postFormFile(BaseHttpClient client, BaseCallback callback);
	void downloadFile(BaseHttpClient client, BaseCallback callback);

	void postFileProgress(BaseHttpClient client, BaseCallback callback);
	void postFormProgress(BaseHttpClient client, BaseCallback callback);
	void execute(BaseHttpClient client, BaseCallback callback);

	/**
	 * 配置初始化操作
	 * @param configuration
	 */
	void init(HttpConfiguration configuration);
}
