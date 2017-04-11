package com.base.apple.demo.api;

import com.apple.http.common.BaseParams;
import com.apple.http.entity.METHOD;
import com.apple.http.retrofit.FieldParam;
import com.apple.http.retrofit.HttpMehod;
import com.apple.http.retrofit.HttpParse;
import com.base.apple.demo.search.bean.UserBean;
import com.base.apple.demo.search.bean.UserReposBean;

/**
 * Created by apple_hsp on 17/4/11.
 */

public interface LoginApiStores {

    @HttpMehod(METHOD.GET)
    @HttpParse(UserBean.class)
    void getUserData(@FieldParam BaseParams baseParams);

    @HttpMehod(METHOD.GET)
    void getUserRepos(@FieldParam BaseParams baseParams);

}
