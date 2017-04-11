package com.apple.web;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
＊
 */
public class BridgeWebViewClientProxy extends WebViewClient {

    private WebViewClient target;

    private IBridgeEngine bridgeEngine;



    public BridgeWebViewClientProxy(WebViewClient target, IBridgeEngine bridgeEngine) {
        this.target = target;
        this.bridgeEngine = bridgeEngine;
    }

    public void setTarget(WebViewClient target) {
        this.target = target;
    }

    public boolean handleBridgeURL(Uri url) {
        if (bridgeEngine.isCorrectProcotocolScheme(url)) {
            if (bridgeEngine.isBridgeLoadedURL(url)) {
                bridgeEngine.injectJavascriptFile();
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!this.handleBridgeURL(Uri.parse(url)) && (null != target)) {
            return target.shouldOverrideUrlLoading(view, url);
        }
        return true;
    }

//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            return handleLoadURL(view, request.getUrl());
//            if (this.handleBridgeURL(request.getUrl())) {
//                return true;
//            } else {
//                return target.shouldOverrideUrlLoading(view, request);
//            }
//        }
//        return false;
//    }


    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            if (null != target) target.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (null != target) target.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (null != target) target.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if (null != target) target.onLoadResource(view, url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (null != target) target.onPageCommitVisible(view, url);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && null != target) {
            return target.shouldInterceptRequest(view, url);
        }
        return null;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && null != target) {
            return target.shouldInterceptRequest(view, request);
        }
        return null;
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (null != target) target.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (null != target) target.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (null != target) target.onReceivedError(view, request, error);
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (null != target) target.onReceivedHttpError(view, request, errorResponse);
        }
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (null != target) target.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (null != target) target.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (null != target) target.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != target) target.onReceivedClientCertRequest(view, request);
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (null != target) target.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (null != target) {
            return target.shouldOverrideKeyEvent(view, event);
        }
        return false;
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (null != target) target.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (null != target) target.onScaleChanged(view, oldScale, newScale);
    }

}
