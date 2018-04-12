package com.bat.fractal.catchwordgame7d.presentation.base;

import com.bat.fractal.catchwordgame7d.common.Constant;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Tung Phan
 * @since 06/30/2017
 */
public class BasePresenter<V extends BaseView> {

    private V view;
    protected CompositeDisposable disposable;
    public static List<Question> questions = new ArrayList<>();
    public static Question firstQuestion;
    public static int timePerQuestion = Constant.DEFAULT_TIME_PER_QUESTION;
    public static int tryTime = Constant.DEFAULT_TRY_TIME;
    public static int numberOfWonQuestion = Constant.DEFAULT_NUMBER_OF_WON_QUESTIONS;

    void setCompositeDisposable(CompositeDisposable disposable) {
        this.disposable = disposable;
    }

    CompositeDisposable getCompositeDisposable() {
        return this.disposable;
    }

    void unsubscribe() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    public void onTakeView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    void onDestroyView() {
        this.view = null;
    }

}
