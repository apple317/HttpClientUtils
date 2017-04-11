package com.apple.web;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
＊
 */
public class SimpleBridgeEngine implements IBridgeEngine {

        Gson gson = new Gson();

        /**
         * 自定义协议Scheme
         */
        private final static String CUSTOM_PROTOCOL_SCHEME = "wjwvjbscheme";

    /**
     * BRIDGE 初始化加载完成
     */
    private final static String BRIDGE_LOADED = "__WJ_BRIDGE_LOADED__";

    /**
     * 获取消息
     */
//    private final static String QUEUE_HAS_MESSAGE = "__WJ_WVJB_QUEUE_MESSAGE__";

    /**
     * JS命令执行器
     */
    private IJSCommandExecuter jsCommandExecuter;


    /**
     * 还未初始化时需要处理消息，消息处理完成就将其清空
     */
    private List<IBridgeMessage> startupMessageQueue;

    /**
     * 响应集合
     */
    private Map<String, IResponseCallback> responseCallbacks;

    /**
     * 接收js消息处理集合
     */
    private Map<String, IRegisterHandler> messageHandlers;

    /**
     * 唯一id标记
     */
    private long uniqueId;


    public SimpleBridgeEngine(IJSCommandExecuter jsCommandExecuter) {
        this.jsCommandExecuter = jsCommandExecuter;
        this.startupMessageQueue = new ArrayList<>();
        this.responseCallbacks = new HashMap<>();
        this.messageHandlers = new HashMap<>();
        this.uniqueId = 0;
    }

    /**
     * 生成回调id
     */
    String generateCallbackId() {
        return "native_cb_" + ++uniqueId;
    }


    @Override
    public void registerHandler(String handlerName, IRegisterHandler handler) {
        this.messageHandlers.put(handlerName,handler);
    }

    @Override
    public void callHandler(String handlerName) {
        this.callHandler(handlerName, null, null);
    }

    @Override
    public void callHandler(String handlerName, IResponseCallback callback) {
        this.callHandler(handlerName, null, callback);
    }

    @Override
    public void callHandler(String handlerName, Object data, IResponseCallback callback) {
        if (null == handlerName) {
//            Log.e("WJWebViewJavascriptBridge", "call handleName不能为空~");
            return;
        }
        SimpleBridgeMessage msg = new SimpleBridgeMessage();
        msg.setData(data);
        msg.setHandlerName(handlerName);
        String callbackId = this.generateCallbackId();
        msg.setCallbackId(callbackId);
        if (null != callback) {
            this.responseCallbacks.put(callbackId, callback);
        }
        this.dispatchMessage(msg);
    }


    @Override
    public boolean isCorrectProcotocolScheme(Uri url) {
        return CUSTOM_PROTOCOL_SCHEME.equals(url.getScheme());
    }

    @Override
    public boolean isBridgeLoadedURL(Uri url) {
        return this.isCorrectProcotocolScheme(url) && BRIDGE_LOADED.equals(url.getHost());
    }

//    @Override
//    public boolean isQueueMessageURL(Uri url) {
//        return false;
//    }

    @Override
    public void injectJavascriptFile() {

        String jsEngineStrin = this.jsCommandExecuter.jsEngineCode();

        this.jsCommandExecuter.evaluateJS(jsEngineStrin);
        if (null != this.startupMessageQueue) {
            List<IBridgeMessage> msgs = this.startupMessageQueue;
            this.startupMessageQueue = null;
            for (IBridgeMessage msg : msgs) {
                this.dispatchMessage(msg);
            }
        }
    }

    @Override
    public void flushMessageQueue(String messageQueue) {
        if (null == messageQueue || messageQueue.length() == 0) {
//            Log.e("WJWebViewJavascriptBridge","接收到无效消息");
            return;
        }
        List<IBridgeMessage> msgs = gson.fromJson(messageQueue, new TypeToken<ArrayList<SimpleBridgeMessage>>(){}.getType());
        for (IBridgeMessage message : msgs) {
            String responseId = message.responseId();
            if (message.isResponse() && this.responseCallbacks.containsKey(responseId)) {
                IResponseCallback responseCallback = this.responseCallbacks.get(responseId);
                responseCallback.responseCallback(message.responseData());
                this.responseCallbacks.remove(responseId);
            } else {
                IResponseCallback responseCallback = null;
                if (message.isCallback()) {
                    final String callbackId = message.callbackId();
                    responseCallback = new IResponseCallback() {
                        @Override
                        public void responseCallback(Object responseData) {
                            SimpleBridgeMessage msg = new SimpleBridgeMessage();
                            msg.setResponseData(responseData);
                            msg.setResponseId(callbackId);
                            dispatchMessage(msg);
                        }
                    };
                } else {
                    responseCallback = new IResponseCallback() {
                        @Override
                        public void responseCallback(Object responseData) {

                        }
                    };
                }

                IRegisterHandler handler = this.messageHandlers.get(message.handlerName());
                if (null != handler) {
                    handler.handler(message.data(), responseCallback);
                }
            }
        }
    }

    @Override
    public void dispatchMessage(IBridgeMessage msg) {
        if (null != this.startupMessageQueue) {
            this.startupMessageQueue.add(msg);
        } else {
            String msgJson = gson.toJson(msg);
            msgJson = msgJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
            msgJson = msgJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
            String jsCommand = this.jsCommandHanldeMessageFormat(msgJson);
            this.jsCommandExecuter.evaluateJS(jsCommand);
        }
    }

//    private String jsCommandFetchQueue() {
//        return "javascript:WebViewJavascriptBridge._fetchQueue();";
//    }

    private String jsCommandHanldeMessageFormat(String messageJSON) {
        if (null != messageJSON) {
            return "javascript:WebViewJavascriptBridge._handleMessageFromNative('" + messageJSON + "');";
        }
        return null;
    }
}
