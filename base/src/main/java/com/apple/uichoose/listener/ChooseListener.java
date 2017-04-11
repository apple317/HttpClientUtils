package com.apple.uichoose.listener;


/**
 * 路由控制器事件
 *
 * @author hushaoping
 */
public interface ChooseListener {
    /**
     * 请求成功
     * @param url  路由地址
     * @param statue 是否跳转
     *               1:成功跳转
     *               2:跳转不了
     */
    abstract void success(String url, int statue);
}
