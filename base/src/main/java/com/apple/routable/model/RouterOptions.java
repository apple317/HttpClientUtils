package com.apple.routable.model;

import android.app.Activity;

import com.apple.routable.IRouterCallback;

import java.util.Map;

/**
 * Created by apple_hsp on 17/6/19.
 */

public class RouterOptions {
    private Animate animate;
    private Map<String, Object> defaultParams;
    private Class<? extends Activity> openClass;
    private IRouterCallback routerCallback;

    private RouterOptions() {
        this.animate = Animate.NONE;
    }

    public void setAnimate(Animate animate) {
        this.animate = animate;
    }

    public void setDefaultParams(Map<String, Object> defaultParams) {
        this.defaultParams = defaultParams;
    }

    public void setOpenClass(Class<? extends Activity> openClass) {
        this.openClass = openClass;
    }

    public void setRouterCallback(IRouterCallback routerCallback) {
        this.routerCallback = routerCallback;
    }

    public Animate getAnimate() {
        return this.animate;
    }

    public Map<String, Object> getDefaultParams() {
        return this.defaultParams;
    }

    public Class<? extends Activity> getOpenClass() {
        return this.openClass;
    }

    public IRouterCallback getRouterCallback() {
        return this.routerCallback;
    }

    public static RouterOptions routerOptions() {
        RouterOptions routerOptions = new RouterOptions();
        return routerOptions;
    }

    public static RouterOptions routerOptionsAsModal() {
        RouterOptions routerOptions = new RouterOptions();
        routerOptions.animate = Animate.MODAL;
        return routerOptions;
    }

    public static RouterOptions routerOptionsForDefaultParams(Map<String, Object> defaultParams) {
        RouterOptions routerOptions = new RouterOptions();
        routerOptions.defaultParams = defaultParams;
        return routerOptions;
    }
}
