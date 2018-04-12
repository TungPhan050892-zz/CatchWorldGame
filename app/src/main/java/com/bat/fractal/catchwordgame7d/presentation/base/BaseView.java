package com.bat.fractal.catchwordgame7d.presentation.base;


import com.bat.fractal.catchwordgame7d.common.ErrorCode;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public interface BaseView {

    void hideLoading();

    void showLoading();

    void showErrorDialog(ErrorCode errorCode);

    BasePresenter getPresenter();
}
