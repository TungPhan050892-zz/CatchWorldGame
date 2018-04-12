package com.bat.fractal.catchwordgame7d.presentation.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;

import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.DialogType;
import com.bat.fractal.catchwordgame7d.presentation.listener.DialogClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by phant on 22-03-18.
 */

public class CustomDialog extends Dialog {

    @BindView(R.id.tv_left_button)
    AppCompatTextView tvLeftButton;
    @BindView(R.id.tv_right_button)
    AppCompatTextView tvRightButton;
    @BindView(R.id.tv_center_button)
    AppCompatTextView tvCenterButton;
    @BindView(R.id.background_image)
    AppCompatImageView backgroundImage;
    @BindView(R.id.face_image)
    AppCompatImageView faceImage;
    private Context context;
    private DialogType dialogType;
    private DialogClickListener listener;

    @OnClick(R.id.tv_left_button)
    void clickLeftButton(View view) {
        listener.clickLeftButton(dialogType);
        dismiss();
    }

    @OnClick(R.id.tv_right_button)
    void clickRightButton(View view) {
        listener.clickRightButton(dialogType);
        dismiss();
    }

    @OnClick(R.id.tv_center_button)
    void clickCenterButton(View view) {
        listener.clickCenterButton(dialogType);
        dismiss();
    }

    public CustomDialog(Context context, DialogType dialogType, DialogClickListener listener) {
        super(context);
        this.context = context;
        this.dialogType = dialogType;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_view);
        ButterKnife.bind(this, this);
        initViewBaseOnDialogType();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void initViewBaseOnDialogType() {
        switch (dialogType) {
            case CORRECT:
                backgroundImage.setBackgroundResource(R.drawable.background_win_a_game);
                backgroundImage.setBackgroundResource(R.drawable.background_win_a_game);
                faceImage.setBackgroundResource(R.drawable.face_win_a_game);
                tvLeftButton.setVisibility(View.GONE);
                tvRightButton.setVisibility(View.GONE);
                tvCenterButton.setText(R.string.next);
                tvCenterButton.setBackgroundResource(R.drawable.button_green_selector);
                tvCenterButton.setVisibility(View.VISIBLE);
                break;
            case TIME_OUT:
                backgroundImage.setBackgroundResource(R.drawable.background_time_out);
                faceImage.setBackgroundResource(R.drawable.face_time_out);
                tvLeftButton.setVisibility(View.VISIBLE);
                tvRightButton.setVisibility(View.VISIBLE);
                tvLeftButton.setText(R.string.back);
                tvLeftButton.setBackgroundDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        R.drawable.button_orange_selector, null));
                tvRightButton.setText(R.string.retry);
                tvRightButton.setBackgroundResource(R.drawable.button_green_selector);
                tvCenterButton.setVisibility(View.GONE);
                break;
            case IN_CORRECT:
                backgroundImage.setBackgroundResource(R.drawable.background_incorrect);
                faceImage.setBackgroundResource(R.drawable.face_incorrect);
                tvLeftButton.setVisibility(View.VISIBLE);
                tvRightButton.setVisibility(View.VISIBLE);
                tvLeftButton.setText(R.string.back);
                tvLeftButton.setBackgroundDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        R.drawable.button_orange_selector, null));
                tvRightButton.setText(R.string.retry);
                tvRightButton.setBackgroundResource(R.drawable.button_green_selector);
                tvCenterButton.setVisibility(View.GONE);
                break;
            case CONGRATULATION:
                backgroundImage.setBackgroundResource(R.drawable.background_congratulation);
                faceImage.setBackgroundResource(R.drawable.face_congratulation);
                tvLeftButton.setVisibility(View.GONE);
                tvRightButton.setVisibility(View.GONE);
                tvCenterButton.setText(R.string.back);
                tvCenterButton.setVisibility(View.VISIBLE);
                tvCenterButton.setBackgroundDrawable(ResourcesCompat.getDrawable(context.getResources(),
                        R.drawable.button_orange_selector, null));
                break;
            case OUT_OF_TIME_TO_PLAY:
                backgroundImage.setBackgroundResource(R.drawable.background_out_of_live);
                faceImage.setBackgroundResource(R.drawable.face_out_of_live);
                tvLeftButton.setVisibility(View.GONE);
                tvRightButton.setVisibility(View.GONE);
                tvCenterButton.setText(R.string.back);
                tvCenterButton.setVisibility(View.VISIBLE);
                tvCenterButton.setBackgroundResource(R.drawable.button_orange_selector);
                break;
            default:
                backgroundImage.setBackgroundResource(R.drawable.background_out_of_live);
                faceImage.setBackgroundResource(R.drawable.face_out_of_live);
                tvLeftButton.setVisibility(View.GONE);
                tvRightButton.setVisibility(View.GONE);
                tvCenterButton.setVisibility(View.VISIBLE);
                break;
        }
    }
}
