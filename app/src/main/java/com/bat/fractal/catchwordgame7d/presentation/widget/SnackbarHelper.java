package com.bat.fractal.catchwordgame7d.presentation.widget;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by admin on 3/27/18.
 */

public class SnackbarHelper {

    public void show(View parentLayout, int message, Snackbar.Callback callback){
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        snackbar.addCallback(callback);
        snackbar.show();
    }

    public void show( View parentLayout, int message){
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

}
