package com.apple.routable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.apple.routable.model.Animate;
import com.apple.routable.model.RouterOptions;
import com.apple.routable.model.RouterParams;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by apple_hsp on 17/6/19.
 */

public class UIRoutable {
    private static volatile UIRoutable sharedInstance;
    private Map<String, RouterOptions> routers = new ConcurrentHashMap();
    private WeakReference<Activity> currentContextWeakRef;
    private Handler dispatchRouterHandler = new Handler(Looper.getMainLooper());

    private UIRoutable() {
    }

    public static UIRoutable defaultRoutable() {
        if(sharedInstance == null) {
            Class var0 = UIRoutable.class;
            synchronized(UIRoutable.class) {
                if(sharedInstance == null) {
                    sharedInstance = new UIRoutable();
                }
            }
        }

        return sharedInstance;
    }

    public void map(String format, IRouterCallback routerCallback) {
        ArrayList formats = new ArrayList();
        formats.add(format);
        this.maps(formats, (IRouterCallback)routerCallback);
    }

    public void maps(List<String> formats, IRouterCallback routerCallback) {
        this.maps(formats, routerCallback, RouterOptions.routerOptions());
    }

    public void map(String format, IRouterCallback routerCallback, RouterOptions routerOptions) {
        ArrayList formats = new ArrayList();
        formats.add(format);
        this.maps(formats, (IRouterCallback)routerCallback, routerOptions);
    }

    public void maps(List<String> formats, IRouterCallback routerCallback, RouterOptions routerOptions) {
        if(null != formats && formats.size() != 0) {
            if(null == routerOptions) {
                routerOptions = RouterOptions.routerOptions();
            }

            routerOptions.setRouterCallback(routerCallback);
            Iterator var4 = formats.iterator();

            while(var4.hasNext()) {
                String f = (String)var4.next();
                this.routers.put(f, routerOptions);
            }

        }
    }

    public void map(String format, Class<? extends Activity> activityClass) {
        ArrayList formats = new ArrayList();
        formats.add(format);
        this.maps(formats, (Class)activityClass);
    }

    public void maps(List<String> formats, Class<? extends Activity> activityClass) {
        this.maps(formats, activityClass, RouterOptions.routerOptions());
    }

    public void map(String format, Class<? extends Activity> activityClass, RouterOptions routerOptions) {
        ArrayList formats = new ArrayList();
        formats.add(format);
        this.maps(formats, (Class)activityClass, routerOptions);
    }

    public void maps(List<String> formats, Class<? extends Activity> activityClass, RouterOptions routerOptions) {
        if(null != formats && formats.size() != 0) {
            if(null == routerOptions) {
                routerOptions = RouterOptions.routerOptions();
            }

            routerOptions.setOpenClass(activityClass);
            Iterator var4 = formats.iterator();

            while(var4.hasNext()) {
                String f = (String)var4.next();
                this.routers.put(f, routerOptions);
            }

        }
    }

    public void openExternal(String url) {
        if(null != url && null != this.currentContextWeakRef && null != this.currentContextWeakRef.get()) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            ((Activity)this.currentContextWeakRef.get()).startActivity(intent);
        }

    }

    public void open(String url) {
        this.open(url, false);
    }

    public void open(String url, boolean animated) {
        this.open(url, (Map)null, animated);
    }

    public void open(String url, Map<String, Object> extraParams) {
        this.open(url, extraParams, false);
    }

    public void open(final String url, final Map<String, Object> extraParams, final boolean animated) {
        if(this.isMainThread()) {
            this.openAction(url, extraParams, animated);
        } else {
            this.dispatchRouterHandler.post(new Runnable() {
                public void run() {
                    try {
                        UIRoutable.this.openAction(url, extraParams, animated);
                    } catch (Exception var2) {
                        Log.d("UIRouter", var2.toString());
                    }

                }
            });
        }

    }

    public void close() {
        this.close(false);
    }

    public void close(final boolean animated) {
        final Activity activity = this.currentActivity();
        if(null != activity) {
            if(this.isMainThread()) {
                activity.finish();
                if(animated && activity.getIntent().getIntExtra("WJ_ROUTER_ACTIVITY_CLOSE_ANIMATE", 0) != 0 && Build.VERSION.SDK_INT >= 5) {
                    activity.overridePendingTransition(activity.getIntent().getIntExtra("WJ_ROUTER_ACTIVITY_CLOSE_ANIMATE", 0), 0);
                }
            } else {
                this.dispatchRouterHandler.post(new Runnable() {
                    public void run() {
                        activity.finish();
                        if(animated && activity.getIntent().getIntExtra("WJ_ROUTER_ACTIVITY_CLOSE_ANIMATE", 0) != 0 && Build.VERSION.SDK_INT >= 5) {
                            activity.overridePendingTransition(activity.getIntent().getIntExtra("WJ_ROUTER_ACTIVITY_CLOSE_ANIMATE", 0), 0);
                        }

                    }
                });
            }
        }

    }

    public Context currentContext() {
        return this.currentActivity();
    }

    private void openAction(String url, Map<String, Object> extraParams, boolean animated) {
        if(null != url) {
            RouterParams params = null;
            params = this.routerParams(url, extraParams);
            if(null != params && null != params.getRouterOptions()) {
                RouterOptions options = params.getRouterOptions();
                if(null != options.getRouterCallback()) {
                    IRouterCallback currentActivity1 = options.getRouterCallback();
                    if(this.isMainThread()) {
                        currentActivity1.callback(url, params.getAllParams());
                    }

                    return;
                }

                Activity currentActivity = this.currentActivity();
                Class activityClass = options.getOpenClass();
                if(null != currentActivity && null != activityClass) {
                    Map allRouterParams = params.getAllParams();
                    Intent intent = new Intent();
                    intent.setClass(currentActivity, activityClass);
                    intent.putExtra("WJ_ROUTER_PARAMS", (HashMap)allRouterParams);
                    intent.putExtra("WJ_OPEN_URL", url);
                    if(Animate.NONE != options.getAnimate()) {
                        intent.putExtra("WJ_ROUTER_ACTIVITY_CLOSE_ANIMATE", options.getAnimate().getCloseAnimValue());
                    }

                    if(allRouterParams.containsKey("WJ_ROUTER_INTENT_FLAG")) {
                        Object flagObject = allRouterParams.get("WJ_ROUTER_INTENT_FLAG");
                        intent.addFlags(Integer.parseInt(flagObject.toString()));
                    }

                    currentActivity.startActivity(intent);
                    if(animated && Animate.NONE != options.getAnimate() && Build.VERSION.SDK_INT >= 5) {
                        currentActivity.overridePendingTransition(options.getAnimate().getOpenAnimValue(), 0);
                    }
                }
            }

        }
    }

    private RouterParams routerParams(String url, Map<String, Object> extraParams) {
        if(null == url) {
            return null;
        } else {
            RouterParams routerParams = null;
            String resolveURL = url;
            int i = url.indexOf("?");
            if(i > 0) {
                resolveURL = url.substring(0, i);
            }

            HashMap mutableExtraParams = new HashMap();
            if(null != extraParams && extraParams.size() > 0) {
                mutableExtraParams.putAll(extraParams);
            }

            Map urlParams = this.resolveURLParams(url);
            if(null != urlParams && urlParams.size() > 0) {
                mutableExtraParams.putAll(urlParams);
            }

            if(this.routers.containsKey(resolveURL)) {
                routerParams = new RouterParams(url, (RouterOptions)this.routers.get(resolveURL), (Map)null, mutableExtraParams);
            } else if(this.routers.size() > 0) {
                String[] givenParts = resolveURL.split("/");
                Iterator iterator = this.routers.keySet().iterator();

                while(iterator.hasNext()) {
                    String format = (String)iterator.next();
                    String[] formatParts = format.split("/");
                    Map givenParams = this.resolveGivenParams(givenParts, formatParts);
                    if(null != givenParams) {
                        routerParams = new RouterParams(url, (RouterOptions)this.routers.get(format), givenParams, mutableExtraParams);
                        break;
                    }
                }
            }

            if(null == routerParams) {
                routerParams = new RouterParams("*", (RouterOptions)this.routers.get("*"), (Map)null, mutableExtraParams);
            }

            return routerParams;
        }
    }

    private Map<String, Object> resolveGivenParams(String[] givenParts, String[] formatParts) {
        HashMap result = null;
        if(givenParts.length == formatParts.length) {
            for(int i = 0; i < formatParts.length; ++i) {
                String givenPart = givenParts[i];
                String formatPart = formatParts[i];
                if(formatPart.startsWith(":")) {
                    if(null == result) {
                        result = new HashMap();
                    }

                    String key = formatPart.substring(1);
                    result.put(key, givenPart);
                } else if(!givenPart.equals(formatPart)) {
                    result = null;
                    break;
                }
            }
        }

        return result;
    }

    private Map<String, Object> resolveURLParams(String url) {
        HashMap params = null;
        if(null != url && url.indexOf("?") > 0) {
            String paramString = url.substring(url.indexOf("?") + 1);
            if(paramString.length() > 0) {
                params = new HashMap();
                String[] items = paramString.split("&");
                String[] var5 = items;
                int var6 = items.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String str = var5[var7];
                    if(str.length() != 0) {
                        int i = str.indexOf("=");
                        if(i > 0) {
                            String key = str.substring(0, i);
                            String val = str.substring(i + 1, str.length());

                            try {
                                String e = URLDecoder.decode(val, "UTF-8");
                                params.put(key, e);
                            } catch (UnsupportedEncodingException var13) {
                                var13.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return params;
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private Activity currentActivity() {
        Activity currentActivity = null;
        if(null != this.currentContextWeakRef) {
            currentActivity = (Activity)this.currentContextWeakRef.get();
        }

        return currentActivity;
    }

    public void register(Activity context) {
        if(null != context) {
            this.currentContextWeakRef = new WeakReference(context);
        }

    }
}
