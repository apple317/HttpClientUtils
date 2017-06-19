package com.apple.tool;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * 泛型工具
 *
 * Created by 吴云海 on 17/4/6.
 */

public final class GenericsUtils {

    private GenericsUtils() {
    }

    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)) {
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            return index < params.length && index >= 0?(!(params[index] instanceof Class)?Object.class:(Class)params[index]):Object.class;
        }
    }

    public static Class getGenericInterfaceGenricType(Class clazz) {
        return getGenericInterfaceGenricType(clazz, (Class)null, 0);
    }

    public static Class getGenericInterfaceGenricType(Class clazz, Class interfaceClass, int index) {
        Type[] types = clazz.getGenericInterfaces();
        Type[] var4 = types;
        int var5 = types.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Type t = var4[var6];
            if(!(t instanceof ParameterizedType)) {
                return Object.class;
            }

            if(null == interfaceClass || ((ParameterizedType)t).getRawType() == interfaceClass) {
                Type[] params = ((ParameterizedType)t).getActualTypeArguments();
                return index < params.length && index >= 0?(!(params[index] instanceof Class)?Object.class:(Class)params[index]):Object.class;
            }
        }

        return Object.class;
    }

}
