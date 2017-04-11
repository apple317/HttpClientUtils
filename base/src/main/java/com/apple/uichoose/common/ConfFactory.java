package com.apple.uichoose.common;

import java.util.HashMap;

/**
 * 定义配置工厂
 * @author hushaoping
 */
public abstract class ConfFactory
{

    public HashMap<String,Class> factoryMap;
    protected ConfFactory() {
    }
    public abstract HashMap<String,Class> createFactory();
}
