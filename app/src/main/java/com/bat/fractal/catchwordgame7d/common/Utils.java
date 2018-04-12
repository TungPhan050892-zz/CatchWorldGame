package com.bat.fractal.catchwordgame7d.common;

import android.os.Build;

/**
 * Created by TungPhan on 12/11/17.
 */
public final class Utils {


    public static boolean higherThanM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean higherThanJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean higherThanKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}
