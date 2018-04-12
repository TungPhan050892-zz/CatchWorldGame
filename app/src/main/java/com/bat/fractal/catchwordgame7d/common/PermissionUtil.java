package com.bat.fractal.catchwordgame7d.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Utility class that wraps access to the runtime permissions API in M and provides basic helper
 * methods.
 */
public abstract class PermissionUtil {

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasRequiredPermissions(Context context, String... permissions) {
        if (permissions.length < 1) {
            return false;
        }
        if (Utils.higherThanM()) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean shouldShowRequestPermission(Activity activity, String... permissions) {
        if (permissions.length < 1) {
            return false;
        }
        if (Utils.higherThanM()) {
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permission)) {
                    return true;
                }
            }
        }
        return false;
    }

}
