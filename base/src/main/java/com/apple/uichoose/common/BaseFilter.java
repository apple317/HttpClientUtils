package com.apple.uichoose.common;


/**
 * 路由跳转
 * 过滤器
 * @author hushaoping
 */
public abstract class BaseFilter
{

   public Class actionClass;

    public abstract boolean executeFilter(String url);

}
