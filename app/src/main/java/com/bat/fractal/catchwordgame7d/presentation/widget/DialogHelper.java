package com.bat.fractal.catchwordgame7d.presentation.widget;

/**
 * Created by apple on 10/18/17.
 */

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.DialogType;
import com.bat.fractal.catchwordgame7d.presentation.listener.DialogClickListener;

public class DialogHelper {

    private Context context;
    private LayoutInflater layoutInflater;

    public DialogHelper(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    public void showNetworkDialog() {
        new MaterialDialog.Builder(context)
                .title(R.string.notice)
                .content(R.string.network_error)
                .negativeText(R.string.ok)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    context.startActivity(intent);
                })
                .cancelable(false)
                .autoDismiss(false)
                .build().show();
    }

    public void showCustomDialog(DialogType dialogType, DialogClickListener listener) {
        CustomDialog customDialog = new CustomDialog(context, dialogType, listener);
        customDialog.setCancelable(false);
        customDialog.show();
    }

}