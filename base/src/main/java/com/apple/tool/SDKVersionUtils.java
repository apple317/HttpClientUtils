package com.apple.tool;

import android.os.Build;

/**
 * @author hao.xiong
 * @version 1.0.0
 */
public class SDKVersionUtils {

    /**
     * hasEclairMR1
     * @return true false
     */
    public static boolean hasEclairMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1;
    }


    /**
     * hasForyo
     * @return true false
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * hasGingerbread
     * @return true false
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * hasHoneycomb
     * @return true false
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * hasHoneycombMR1
     * @return true false
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * hasHoneycombMR2
     * @return true false
     */
    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * hasIceCreamSandwich
     * @return true false
     */
    public static boolean hasIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * hasJellyBean
     * @return true false
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    private static final int JELLY_BEAN_MR1 = 17; // Build.VERSION_CODES.JELLY_BEAN_MR1
    /**
     * 4.2以上
     * @return true false
     */
    public static boolean hasJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= JELLY_BEAN_MR1;
    }

    private static final int JELLY_BEAN_MR2 = 18;

    /**
     * 4.3及以上
     * @return 4.3及以上
     */
    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= JELLY_BEAN_MR2;
    }

    private static final int KITKAT = 19;

    /**
     * 4.4及以上
     * @return 4.4及以上
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= KITKAT;
    }
}
