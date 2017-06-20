package com.jucaicat.market.modules.main;

import android.app.Application;
import android.content.Intent;

import com.jucaicat.market.common.model.AppBaseModuleObject;
import com.jucaicat.market.constants.UIRouterKeys;
import com.jucaicat.market.modules.main.model.HomePage;
import com.wj.uiroutable.UIRoutable;
import com.wj.uiroutable.constants.RouterConstant;
import com.wj.uiroutable.model.RouterOptions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 首页模块
 *
 * Created by terence.wang on 2017/3/7.
 */

public class MainModule extends AppBaseModuleObject {


    @Override
    protected void routerMapAction() {
        super.routerMapAction();
        //首页
        Map<String, Object> homeParams = new HashMap<>();
        homeParams.put(RouterConstant.WJ_ROUTER_INTENT_FLAG, Intent.FLAG_ACTIVITY_TASK_ON_HOME|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        homeParams.put("homeIndex", HomePage.HOME.getCode());
        UIRoutable.defaultRoutable().map(UIRouterKeys.UI_ROUTER_URL_MAIN, MainActivity.class, RouterOptions.routerOptionsForDefaultParams(homeParams));


        //产品列表
        Map<String, Object> productListParams = new HashMap<>();
        productListParams.put(RouterConstant.WJ_ROUTER_INTENT_FLAG, Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        productListParams.put("homeIndex", HomePage.PRODUCT_LIST.getCode());
        UIRoutable.defaultRoutable().map(UIRouterKeys.UI_ROUTER_URL_PRODUCT_BASELIST, MainActivity.class, RouterOptions.routerOptionsForDefaultParams(productListParams));


        //主会场
        Map<String, Object> venueParams = new HashMap<>();
        venueParams.put(RouterConstant.WJ_ROUTER_INTENT_FLAG, Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        venueParams.put("homeIndex", HomePage.VENUE.getCode());
        UIRoutable.defaultRoutable().map(UIRouterKeys.UI_ROUTER_URL_VENUE, MainActivity.class, RouterOptions.routerOptionsForDefaultParams(venueParams));

        //资产
        Map<String, Object> assetParams = new HashMap<>();
        assetParams.put(RouterConstant.WJ_ROUTER_INTENT_FLAG, Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        assetParams.put("homeIndex", HomePage.ASSET.getCode());
        UIRoutable.defaultRoutable().map(UIRouterKeys.UI_ROUTER_URL_ASSETS, MainActivity.class, RouterOptions.routerOptionsForDefaultParams(assetParams));


        //我
        Map<String, Object> myParams = new HashMap<>();
        myParams.put(RouterConstant.WJ_ROUTER_INTENT_FLAG, Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        myParams.put("homeIndex", HomePage.MY.getCode());
        UIRoutable.defaultRoutable().map(UIRouterKeys.UI_ROUTER_URL_MY, MainActivity.class, RouterOptions.routerOptionsForDefaultParams(myParams));
    }



}
