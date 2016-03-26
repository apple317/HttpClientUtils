# okhttp-utils
对okhttp的封装类，okhttp见：[https://github.com/square/okhttp](https://github.com/square/okhttp).



## 用法

* Android Studio

	使用前，对于Android Studio的用户，可以选择添加:

	```
	compile project(':HttpClientUtils')
	```
	


**注意**

目前最新版本，提供了自定Callback，可以按照下面的方式，自行解析返回结果：





##目前支持
* 一般的get请求
* 一般的post请求
* 基于Http Post的文件上传（类似表单）
* 文件下载/加载图片  not
* 上传下载的进度回调 not
* 支持session的保持  
* 支持自签名网站https的访问，提供方法设置下证书就行
* 支持取消某个请求
* 支持自定义Callback
* 支持HEAD、DELETE、PATCH、PUT  not

##用法示例

### GET请求

```java
 void initData(){
        initParameter();
        mParams.put("appkey", "56065429");
        mParams.put("sign", "AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB");
        BaseHttpClient.getOkClient(getApplicationContext()).sendGetRequest("http://api.dianping.com/v1/metadata/get_cities_with_deals", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
    }
     protected  void initParameter(){
        if (mParams == null) {
            mParams = new BaseParams();
        }else {
            mParams.clear();
        }
    }
```

### POST请求 提交一个Gson字符串到服务器端。

```java
 initParameter();
        mParams.put("os", "2");
        mParams.put("device_id", "123871827312");
        mParams.put("version", "1.1");
        mParams.put("game", "dota");
        BaseHttpClient.getOkClient(getApplicationContext()).sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });

```



### Post File

```java
initParameter();
        mParams.put("os", "2");
        File file=new File("app.png");
        try {
            //logo 是文件上传定义的名词，根据服务端定义调整
            mParams.put("logo", file, FileNameGenerator.getMIMEType(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BaseHttpClient.getOkClient(getApplicationContext()).sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
```
将文件作为请求体，发送到服务器。


### Post表单形式上传文件

```java
initParameter();
        mParams.put("os", "2");
        File file=new File("app.png");
        try {
            //logo 是文件上传定义的名词，根据服务端定义调整
            mParams.put("logo", file, FileNameGenerator.getMIMEType(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BaseHttpClient.getOkClient(getApplicationContext()).sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
```

支持单个多个文件，`addFile`的第一个参数为文件的key，即类别表单中`<input type="file" name="mFile"/>`的name属性。

### 自定义CallBack




### 取消单个请求

```java
未开放接口。正在实现。
 
Call call=BaseHttpClient.getBaseClient(getApplicationContext()).sendGetRequest("http://api.dianping.com/v1/metadata/get_cities_with_deals", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
 call.cancel();

### 根据tag取消请求

目前对于支持的方法都添加了最后一个参数`Object tag`，取消则通过执行。
第一个参数就是tag传入。
BaseHttpClient.getBaseClient(getApplicationContext()).sendGetRequest("get","http://api.dianping.com/v1/metadata/get_cities_with_deals", mParams,new HttpCallback(){
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
例如：在Activity中，当Activity销毁取消请求：
```


```
比如，在请求中传入tag对象，销魂时候可以统一销毁同一tag对象访问
@Override
protected void onDestroy()
{
    super.onDestroy();
     //可以取消同一个tag的
     BaseHttpClient.getBaseClient(getApplicationContext()).cancelTag("tag对象");
}
## 混淆

```

#okhttp





```






