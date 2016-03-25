okhttp-utils
对okhttp的封装类，okhttp见：https://github.com/square/okhttp.

用法

Android Studio

使用前，对于Android Studio的用户，可以选择添加:

compile project(':HttpClientUtils')

注意

目前最新版本提供了自定Callback，可以按照下面的方式，自行解析返回结果：


目前支持

一般的get请求
一般的post请求
基于Http Post的文件上传（类似表单）
文件下载/加载图片 正在开发
上传下载的进度回调 正在开发
支持session的保持   
支持自签名网站https的访问，提供方法设置下证书就行
支持取消某个请求 
支持自定义Callback
用法示例
GET请求
   
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
    /**
     * 初始化参数
     */
    protected  void initParameter(){
        if (mParams == null) {
            mParams = new BaseParams();
        }else {
            mParams.clear();
        }
    }
Post请求数据
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
Post文件请求
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


