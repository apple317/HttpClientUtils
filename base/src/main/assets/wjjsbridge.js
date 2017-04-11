(function () {

    //如果WebViewJavascriptBridge存在则返回
    if (window.WebViewJavascriptBridge) {
        return;
    }

    if (!window.onerror) {
        window.onerror = function (msg, url, line) {
            console.log("WJWebViewJavascriptBridge: ERROR:" + msg + "@" + url + ":" + line);
        }
    }


    window.WebViewJavascriptBridge = {
        registerHandler: registerHandler,
        callHandler: callHandler,
        disableJavscriptAlertBoxSafetyTimeout: disableJavscriptAlertBoxSafetyTimeout,
        _fetchQueue: _fetchQueue,
        _handleMessageFromNative: _handleMessageFromNative
    };

    //消息iframe
    var messagingIframe;
    //发送消息队列
    var sendMessageQueue = [];
    //消息处理器集合
    var messageHandlers = {};

    //自定义协议Scheme
    var CUSTOM_PROTOCOL_SCHEME = 'wjwvjbscheme';
    //消息队列Host
    var QUEUE_HAS_MESSAGE = '__WJ_WVJB_QUEUE_MESSAGE__';
    //响应回调器
    var responseCallbacks = {};
    //唯一id
    var uniqueId = 1;
    //分派消息超时的安全
    var dispatchMessagesWithTimeoutSafety = true;

    //注册处理器
    function registerHandler(handlerName, handler) {
        //保存消息处理器
        messageHandlers[handlerName] = handler;
    }
    //调用Native 代码
    function callHandler(handlerName, data, responseCallback) {
        if (arguments.length == 2 && typeof data == 'function') {
            responseCallback = data;
            data = null;
        }
        _doSend({ handlerName: handlerName, data: data }, responseCallback);
    }

    //关闭分派消息超时的安全
    function disableJavscriptAlertBoxSafetyTimeout() {
        dispatchMessagesWithTimeoutSafety = false;
    }
    //组装消息发送到Native
    function _doSend(message, responseCallback) {
        if (responseCallback) {
            var callbackId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();
            responseCallbacks[callbackId] = responseCallback;
            message['callbackId'] = callbackId;
        }
        //将消息装入队列
        sendMessageQueue.push(message);
        //调用Native，让其取消息
//        messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE;
        _fetchQueue();
    }
    //native 获取队列消息函数
    function _fetchQueue() {
        var messageQueueString = JSON.stringify(sendMessageQueue);
        sendMessageQueue = [];
        window.jsCallback.callback(messageQueueString);
    }

    //调度处理来自Native的消息
    function _dispatchMessageFromNative(messageJSON) {
        if (dispatchMessagesWithTimeoutSafety) {
            setTimeout(_doDispatchMessageFromNative);
        } else {
            _doDispatchMessageFromNative();
        }
        //执行来自native的消息
        function _doDispatchMessageFromNative() {
            var message = JSON.parse(messageJSON);
            var messageHandler;
            var responseCallback;

            if (message.responseId) {
                responseCallback = responseCallbacks[message.responseId];
                if (!responseCallback) {
                    return;
                }
                responseCallback(message.responseData);
                delete responseCallbacks[message.responseId];
            } else {
                if (message.callbackId) {
                    var callbackResponseId = message.callbackId;
                    responseCallback = function (responseData) {
                        _doSend({ handlerName: message.handlerName, responseId: callbackResponseId, responseData: responseData });
                    };
                }

                var handler = messageHandlers[message.handlerName];
                if (!handler) {
                    console.log("WJWebViewJavascriptBridge: WARNING: no handler for message from Native:", message);
                } else {
                    handler(message.data, responseCallback);
                }
            }
        }
    }
    //处理来自native的消息
    function _handleMessageFromNative(messageJSON) {
        _dispatchMessageFromNative(messageJSON);
    }

    //插件一个iframe元素用来发送消息给native
    messagingIframe = document.createElement('iframe');
    messagingIframe.style.display = 'none';
    messagingIframe.src = CUSTOM_PROTOCOL_SCHEME + '://' + QUEUE_HAS_MESSAGE;
    document.documentElement.appendChild(messagingIframe);

    registerHandler("_disableJavascriptAlertBoxSafetyTimeout", disableJavscriptAlertBoxSafetyTimeout);

    setTimeout(_callWVJBCallbacks, 0);
    function _callWVJBCallbacks() {
        var callbacks = window.WVJBCallbacks;
        delete window.WVJBCallbacks;
        for (var i = 0; i < callbacks.length; i++) {
            callbacks[i](WebViewJavascriptBridge);
        }
    }
})();