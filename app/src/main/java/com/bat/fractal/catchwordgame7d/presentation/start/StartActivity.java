package com.bat.fractal.catchwordgame7d.presentation.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.bat.fractal.catchwordgame7d.App;
import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.Constant;
import com.bat.fractal.catchwordgame7d.common.ErrorCode;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.model.Settings;
import com.bat.fractal.catchwordgame7d.datalayer.repository.CatchWordGameDataRepository;
import com.bat.fractal.catchwordgame7d.presentation.base.BaseActivity;
import com.bat.fractal.catchwordgame7d.presentation.base.BasePresenter;
import com.bat.fractal.catchwordgame7d.presentation.main.MainActivity;
import com.bat.fractal.catchwordgame7d.presentation.main.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StartActivity extends BaseActivity<StartPresenter> implements StartView {

    private static final String TAG = StartActivity.class.getSimpleName();

    @BindView(R.id.view_parent)
    ConstraintLayout viewParent;
    @Inject
    CatchWordGameDataRepository repository;

    @OnClick(R.id.button_start)
    void clickStart(View view) {
        //start new activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_volume)
    void clickVolume(View view) {
        //turn on/off volume
        if (volumeIsOn) {
            volumeIsOn = false;
            view.setBackgroundResource(R.drawable.button_volume_off_selector);
            musicPlayer.setVolume(Constant.MIN_VOLUME, Constant.MIN_VOLUME);
        } else {
            volumeIsOn = true;
            view.setBackgroundResource(R.drawable.button_volume_on_selector);
            musicPlayer.setVolume(Constant.MAX_VOLUME, Constant.MAX_VOLUME);
        }
    }

    @OnClick(R.id.button_close)
    void clickClose(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInjector();
        setPresenter(new StartPresenter(repository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getInstance().isConnectToNetwork()) {
            if (getPresenter().questions.size() <= 0) {
                showLoadingDialog(R.string.get_data);
                getPresenter().getData();
            }
        } else {
            dialogHelper.showNetworkDialog();
        }
    }

    @Override
    public void initFirstQuestion(Question question) {
        getPresenter().firstQuestion = question;
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initSetting(Settings settings) {
        if (settings != null) {
            if (!settings.getTimePerQuestion().isEmpty()) {
                getPresenter().timePerQuestion = Integer.parseInt(settings.getTimePerQuestion());
            }
            if (!settings.getTryTimes().isEmpty()) {
                getPresenter().tryTime = Integer.parseInt(settings.getTryTimes());
            }
            if (!settings.getNumberOfWonQuestion().isEmpty()) {
                getPresenter().numberOfWonQuestion = Integer.parseInt(settings.getNumberOfWonQuestion());
            }
        } else {
            //TODO: notify user with dialog
        }
    }

    @Override
    public void initTheRestOfQuestions(List<Question> questionList) {
        getPresenter().questions = questionList;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showErrorDialog(ErrorCode errorCode) {
        snackbarHelper.show(viewParent, errorCode.getMessage());
    }
}
