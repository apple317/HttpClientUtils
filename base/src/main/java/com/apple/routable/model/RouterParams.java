package com.apple.routable.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple_hsp on 17/6/19.
 */

public class RouterParams {
    private RouterOptions routerOptions;
    private Map<String, Object> openParams;
    private Map<String, Object> extraParams;
    private String url;

    public String getUrl() {
        return this.url;
    }

    public RouterParams(String url, RouterOptions routerOptions, Map<String, Object> openParams, Map<String, Object> extraParams) {
        this.url = url;
        this.routerOptions = routerOptions;
        this.openParams = openParams;
        this.extraParams = extraParams;
    }

    public Map<String, Object> getAllParams() {
        HashMap result = new HashMap();
        if(null != this.openParams && this.openParams.size() > 0) {
            result.putAll(this.openParams);
        }

        if(null != this.extraParams && this.extraParams.size() > 0) {
            result.putAll(this.extraParams);
        }

        if(null != this.url) {
            result.put("WJ_OPEN_URL", this.url);
        }

        return result;
    }

    public RouterOptions getRouterOptions() {
        return this.routerOptions;
    }

    public void setRouterOptions(RouterOptions routerOptions) {
        this.routerOptions = routerOptions;
    }

    public Map<String, Object> getOpenParams() {
        return this.openParams;
    }

    public void setOpenParams(Map<String, Object> openParams) {
        this.openParams = openParams;
    }

    public Map<String, Object> getExtraParams() {
        return this.extraParams;
    }

    public void setExtraParams(Map<String, Object> extraParams) {
        this.extraParams = extraParams;
    }

}
