package com.base.apple.demo.search.viewmodel;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.apple.common.ViewModel;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.entity.METHOD;
import com.apple.http.listener.HttpCallback;
import com.base.apple.demo.MainBinding;
import com.base.apple.demo.R;
import com.base.apple.demo.api.LoginApiStores;
import com.base.apple.demo.common.AppBaseParams;
import com.base.apple.demo.search.bean.UserBean;
import com.base.apple.demo.search.bean.UserReposBean;
import com.base.apple.demo.search.view.UserListAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by kelin on 16-4-28.
 */
public class MainViewModel extends ViewModel implements View.OnClickListener {


    UserListAdapter userListAdapter;

    MainBinding mainActivityBinding;


    InputMethodManager inputMethodManager;
    List<UserBean.ItemsBean> userData;

    HashMap<String, Integer> userIndex;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Log.e("HU", "btn_search====");
                mainActivityBinding.mainProgress.setVisibility(View.VISIBLE);
                AppBaseParams appBaseParams = new AppBaseParams();
                BaseHttpClient.newBuilder().urlIdentifier("search_user").
                        url("https://api.github.com/search/users?q=" + mainActivityBinding.editSearch.getText().toString())
                        .build().execute(LoginApiStores.class, this).getUserData(appBaseParams);
                break;
        }
    }


    @Override
    public void initView(Bundle bundle) {
        mainActivityBinding = DataBindingUtil.setContentView(act, R.layout.main);
    }

    @Override
    public void initData(Bundle bundle) {
        userIndex = new HashMap<>();
        userListAdapter = new UserListAdapter(act);
        mainActivityBinding.listSerach.setAdapter(userListAdapter);
        mainActivityBinding.btnSearch.setOnClickListener(this);
        inputMethodManager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    Activity act;

    public MainViewModel(Activity activity) {
        this.act = activity;
    }


    @Override
    public void initLintener(Bundle bundle) {

    }


    @Override
    public void success(String content, BaseHttpClient client, Object parse) {
        super.success(content, client, parse);
        switch (client.getUrlIdentifier()) {
            case "search_user":
                mainActivityBinding.mainProgress.setVisibility(View.GONE);
                inputMethodManager.hideSoftInputFromWindow(act.getWindow().getDecorView().getWindowToken(),
                        0);
                final UserBean userBean = (UserBean) parse;
                if (userBean != null) {
                    userData = userBean.getItems();
                    for (int k = 0; k < userData.size(); k++) {
                        Log.e("HU", "userDatauserDatauserData===22");
                        userIndex.put(userData.get(k).getLogin(), k);
                        BaseHttpClient.newBuilder().urlIdentifier(userData.get(k).getLogin()).
                                url("https://api.github.com/users/" + userData.get(k).getLogin() + "/repos")
                                .method(METHOD.GET)
                                .build().execute(new HttpCallback() {
                            @Override
                            public void success(String content, BaseHttpClient client, Object parse) {
                                Log.e("HU", "content====" + content);
                                try {
                                    Gson gson = new Gson();
                                    JSONArray jsonArray = new JSONArray(content);
                                    HashMap<String, Integer> map = new HashMap<String, Integer>();
                                    if (jsonArray != null && jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            UserReposBean userReposBean = gson.fromJson(jsonArray.get(i).toString(), UserReposBean.class);
                                            String name = userReposBean.getOwner().getLogin();
                                            String language = userReposBean.getLanguage();
                                            if(language!=null){
                                                if (map.get(name + "_" + language) != null) {
                                                    map.put(name + "_" + language, map.get(name + "_" + language) + 1);
                                                } else {
                                                    map.put(name + "_" + language, 1);
                                                }
                                            }
                                        }
                                        Collection<Integer> count = map.values();
                                        int maxCount = Collections.max(count);
                                        StringBuilder language=new StringBuilder();
                                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                            // 得到value为maxCount的key，也就是数组中出现次数最多的数字
                                            String key=entry.getKey().replaceFirst(client.getUrlIdentifier()+"_","");
                                            if(maxCount==1){
                                                language.append(key+" ");
                                            }else if(maxCount>1&&maxCount==entry.getValue()){
                                                language.append(key+" ");
                                            }
                                        }
                                        userBean.getItems().get(userIndex.get(client.getUrlIdentifier())).setLanguage(language.toString().trim().isEmpty()
                                        ?"么有偏爱语言或者查询失败":language.toString());
                                    }



                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void error(Throwable error, BaseHttpClient client) {

                            }
                        });
                    }
                    userListAdapter.setData(userData);
                }
                break;
        }


    }

    @Override
    public void error(Throwable error, BaseHttpClient client) {
        super.error(error, client);
        mainActivityBinding.mainProgress.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }
}
