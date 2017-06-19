package com.apple.routable;

import android.app.Activity;
import android.content.Context;

import com.apple.routable.model.RouterOptions;

import java.util.List;
import java.util.Map;

/**
 * Created by apple_hsp on 17/6/19.
 */

public interface IRouterAction {
    void openExternal(String var1);

    void open(String var1);

    void open(String var1, boolean var2);

    void open(String var1, Map<String, Object> var2);

    void open(String var1, Map<String, Object> var2, boolean var3);

    Context currentContext();

    void close();

    void close(boolean var1);

    void map(String var1, IRouterCallback var2);

    void maps(List<String> var1, IRouterCallback var2);

    void map(String var1, IRouterCallback var2, RouterOptions var3);

    void maps(List<String> var1, IRouterCallback var2, RouterOptions var3);

    void map(String var1, Class<? extends Activity> var2);

    void maps(List<String> var1, Class<? extends Activity> var2);

    void map(String var1, Class<? extends Activity> var2, RouterOptions var3);

    void maps(List<String> var1, Class<? extends Activity> var2, RouterOptions var3);

}
