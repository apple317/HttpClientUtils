package com.apple.butterknife;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.apple.app.BaseFragment;

import java.lang.reflect.Method;


public class ViewBinder {
    public ViewBinder() {
    }
    public enum Finder {
        FRAGMENT {
            @Override
            protected View intView(Object source, int id) {
                return   LayoutInflater.from(getContext(source)).inflate(id,null);
            }

            @Override
            protected View findView(Object source, int id) {
                return ((Fragment) source).getView().findViewById(id);
            }

            @Override
            public Context getContext(Object source) {
                return ((Fragment) source).getContext();
            }
        },

        VIEW {
            @Override
            protected View intView(Object source, int id) {
                return LayoutInflater.from(getContext(source)).inflate(id,null);
            }

            @Override
            protected View findView(Object source, int id) {
                return ((View) source).findViewById(id);
            }

            @Override
            public Context getContext(Object source) {
                return ((View) source).getContext();
            }
        },
        ACTIVITY {
            @Override
            protected View intView(Object source, int id) {
                return LayoutInflater.from(getContext(source)).inflate(id,null);
            }

            @Override
            protected View findView(Object source, int id) {
                return ((Activity) source).findViewById(id);
            }

            @Override
            public Context getContext(Object source) {
                return (Activity) source;
            }
        },
        DIALOG {
            @Override
            protected View intView(Object source, int id) {
                return LayoutInflater.from(getContext(source)).inflate(id,null);
            }

            @Override
            protected View findView(Object source, int id) {
                return ((Dialog) source).findViewById(id);
            }

            @Override
            public Context getContext(Object source) {
                return ((Dialog) source).getContext();
            }
        };

        protected abstract View intView(Object source, int id);

        protected abstract View findView(Object source, int id);

        public abstract Context getContext(Object source);
    }

    public View initView(Activity activity) {
        return  initView(activity,Finder.ACTIVITY);
    }

    public View initView(Dialog dialog) {
        return  initView(dialog,Finder.DIALOG);
    }

    public View initView(BaseFragment fragment) {
        return  initView(fragment,Finder.FRAGMENT);
    }



    /**
     * 初始化查找 view
     * @param target
     * @param finder
     * @return
     */
    View initView(Object target, Finder finder) {
        Method[] mts = target.getClass().getDeclaredMethods();
        for (Method mt : mts) {
            BindView ad = mt.getAnnotation(BindView.class);//如果方法上  没有该注解  则返回null
            if (ad != null) {
                int value = ad.value();
                return finder.intView(target,value);
            }
        }
        return  null;
    }

}
