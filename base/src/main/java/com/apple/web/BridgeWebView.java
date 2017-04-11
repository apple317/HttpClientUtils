package com.apple.web;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 */
public class BridgeWebView extends WebView implements IWebViewJavascriptBridge,IJSCommandExecuter {

    final Handler uiHander = new Handler();

    /**
     * 桥接引擎
     */
    private IBridgeEngine bridgeEngine;

    /**
     * js引擎代码
     */
    private static String jsEngineCode = null;

    private BridgeWebViewClientProxy webViewClientProxy;

    public BridgeWebView(Context context) {
        super(context);
        this.initParams();
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initParams();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initParams();
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (client == this.webViewClientProxy) {
            super.setWebViewClient(client);
        } else {
            this.webViewClientProxy.setTarget(client);
        }
    }


    private void initParams() {

        this.bridgeEngine = new SimpleBridgeEngine(this);
        this.webViewClientProxy = new BridgeWebViewClientProxy(null, this.bridgeEngine);
        this.setWebViewClient(this.webViewClientProxy);
        if (isInEditMode()) { return; }
        this.getSettings().setJavaScriptEnabled(true);
        this.addJavascriptInterface(new IJSCallback() {
            @Override
            @JavascriptInterface
            public void callback(String messageData) {

                final String msgData = messageData;
                uiHander.post(new Runnable() {
                    @Override
                    public void run() {
                        bridgeEngine.flushMessageQueue(msgData);
                    }
                });


//                bridgeEngine.flushMessageQueue(messageData);
            }
        }, "jsCallback");
    }

    @Override
    public void evaluateJS(String command) {
        this.loadUrl(command);
    }

    @Override
    public String jsEngineCode() {
        if (null == jsEngineCode) {
            jsEngineCode = assetFile2Str(this.getContext(), "wjjsbridge.js");
            jsEngineCode = "javascript:" + jsEngineCode;
        }
        return jsEngineCode;
    }

    @Override
    public void registerHandler(String handlerName, IRegisterHandler handler) {
        this.bridgeEngine.registerHandler(handlerName,handler);
    }

    @Override
    public void callHandler(String handlerName) {
        this.bridgeEngine.callHandler(handlerName);
    }

    @Override
    public void callHandler(String handlerName, IResponseCallback callback) {
        this.bridgeEngine.callHandler(handlerName, null, callback);
    }

    @Override
    public void callHandler(String handlerName, Object data, IResponseCallback callback) {
        this.bridgeEngine.callHandler(handlerName, data, callback);
    }

    public String assetFile2Str(Context c, String urlStr){
        InputStream in = null;
        try{
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
