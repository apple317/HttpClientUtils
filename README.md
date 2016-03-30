# okhttp-utils
对okhttp的封装类，okhttp见：https://github.com/apple317/HttpClientUtils
博客地址：http://blog.csdn.net/apple_hsp/article/details/50986497


## 用法

* Android Studio

	使用前，对于Android Studio的用户，可以选择添加:

	```
	compile project(':HttpClientUtils')
	```
＊代码直接copy到项目中


**注意**

目前最新版本，提供了自定Callback，可以按照下面的方式，自行解析返回结果：





##目前支持
* 一般的get请求 已实现
* 一般的post请求 已实现<post文本、表单、文件上传>
* 基于Http Post的文件上传（类似表单）已实现
* 文件下载/加载图片  已实现
* 上传下载的进度回调 已实现
* 支持Head           已实现
* 支持取消某个请求   已实现
* 支持url取消请求    已实现
* 支持tag取消请求    已实现
* 支持自定义Callback   已实现
* 支持DELETE、PATCH、PUT  下版本实现
* 支持session的保持  下版本实现
* 支持自签名网站https的访问，提供方法设置下证书就行
##用法示例

### GET请求

```java
 BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                .put("appkey","56065429").put("sign","AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB").
                getRequest(new HttpCallback(){
            @Override
            public void onSuccess(String content, Object object, String reqType) {
                txt_content.setText(content+"type==="+reqType);

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
             @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
            }
        });
```

### POST请求 提交一个Gson字符串到服务器端。

```java
第一种方式
默认是表单上传，所以在这里分离postStringRequest方法，而文本必须是apple_txt为key传入
BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                .put("apple_txt","你好好好").put("sign","AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB").
                postStringRequest(new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {
                txt_content.setText(content + "type===" + reqType);

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
             @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
            }
        });
第二种方式
 BaseParams mParams = new BaseParams();
        mParams.put("apple_txt", "你好好");

        BaseHttpClient.getBaseClient().sendPostStringRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
             @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
            }
        });
```



### Post File

```java
第一种方式
 	BaseParams mParams = new BaseParams();
        mParams.put("game", "dota");
        mParams.put("os", "2");
        File file=new File("app.png");
        try {
            //logo 是文件上传定义的名词，根据服务端定义调整
            mParams.put("logo", file, FileNameGenerator.getMIMEType(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BaseHttpClient.getOkClient(getApplicationContext()).
        sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
            @Override
            public void onSuccess(String content, Object object, String reqType) {

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
             @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
            }
        });
第二种方式
 try {
            BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                    .put("logo",file).postRequest(new HttpCallback() {
                @Override
                public void onSuccess(String content, Object object, String reqType) {
                    txt_content.setText(content + "type===" + reqType);

                }

                @Override
                public void onFailure(Throwable error, String content, String reqType) {

                }
                 @Override
            	public void onProgress(long bytesRead, long contentLength, boolean done) {
        	 }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
```
将文件作为请求体，发送到服务器。


### Post支持多个文件上传

```java
try {
            File file=new File("path");
            ArrayList<File> arrayList=new ArrayList<File>();
            arrayList.add(file);
            BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                    .put("logo",arrayList).postRequest(new HttpCallback() {
                @Override
                public void onSuccess(String content, Object object, String reqType) {
                    txt_content.setText(content + "type===" + reqType);

                }

                @Override
                public void onFailure(Throwable error, String content, String reqType) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
```

### 自定义CallBack

### 上传文件进度条返回
```java
BaseHttpClient.getBaseClient().addUrl("url").put("game","lol").put("logo",file)
                    .postRequest(new HttpCallback() {
                        @Override
                        public void onSuccess(String content, Object object, String reqType) {
                            Message msg = new Message();
                            msg.obj = content;
                            msg.what = 0;
                            mHandler.sendMessage(msg);
                        }

                        @Override
                        public void onFailure(Throwable error, String content, String reqType) {
                            Log.i("HU", "onFailure=content=" + content);
                            Log.i("HU", "onFailure=error=" + error);


                        }

                        @Override
                        public void onProgress(long bytesRead, long contentLength, boolean done) {
                            Log.i("HU", "onprogress=bytesRead=" + bytesRead);
                            Message msg = new Message();
                            msg.obj = bytesRead * 1.0f / contentLength;
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
进度会在onProgress文件中返回，
bytesread就是当前传入字节数
contentLength就是总共字节数
done如果为true下载完毕
```
### 下载文件进度条返回 查看demo:DownFileActivity
```java
 /**
         * 第一种写法
         */
        BaseHttpClient.getBaseClient().addUrl("http://119.188.38.18/65738BF87E9458339EB7752598/030008010056B56763489B144DDF25C34CD015-9088-4C46-DE06-105A955F87E1.mp4.ts?ts_start=5.906&ts_end=9.076&ts_seg_no=1&ts_keyframe=1===info==1")
                .downName("apple_nba").downloadFile(getApplicationContext(),new DownCallback() {
            @Override
            public void onProgress(DownEntity entity) {
                Message msg = new Message();
                msg.obj = entity;
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        });
进度会在onProgress文件中返回下载实体类
downName传入文件名称
downEntity类中返回
public String name;//名称
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
```
### 取消单个请求

```java
 支持单个操作删除，通过url传入来关闭请求。
  BaseHttpClient.getBaseClient().cancel("url");

 


### 根据tag取消请求

目前对于支持的方法都添加了最后一个参数`Object tag`，取消则通过执行。
第一个参数就是tag传入。
 BaseHttpClient.getBaseClient().addUrl("http://api.dianping.com/v1/metadata/get_cities_with_deals")
                .put("appkey","56065429").put("sign","AF24BF8A3F31D22D25422BCDD86AA322F43B5BAB")
                .setTag("deals").getRequest(new HttpCallback(){
            @Override
            public void onSuccess(String content, Object object, String reqType) {
                txt_content.setText(content+"type==="+reqType);

            }

            @Override
            public void onFailure(Throwable error, String content, String reqType) {

            }
        });
传入tag，使用方法setTag,tag是Object对象。
第二种方式
BaseParams mParams = new BaseParams();
        mParams.put("game", "dota");
        mParams.setTag("tag");//设置tag传入
        BaseHttpClient.getBaseClient()
        .sendPostRequest("http://apphttpurl.com/v1", mParams, new HttpCallback() {
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
     BaseHttpClient.getBaseClient().cancelTag("tag对象");
}
## 混淆

```

#okhttp





```






