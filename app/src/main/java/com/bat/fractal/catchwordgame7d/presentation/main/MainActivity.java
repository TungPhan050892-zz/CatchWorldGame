package com.bat.fractal.catchwordgame7d.presentation.main;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bat.fractal.catchwordgame7d.App;
import com.bat.fractal.catchwordgame7d.R;
import com.bat.fractal.catchwordgame7d.common.Constant;
import com.bat.fractal.catchwordgame7d.common.DialogType;
import com.bat.fractal.catchwordgame7d.common.ErrorCode;
import com.bat.fractal.catchwordgame7d.datalayer.model.Answer;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.repository.CatchWordGameDataRepository;
import com.bat.fractal.catchwordgame7d.presentation.base.BaseActivity;
import com.bat.fractal.catchwordgame7d.presentation.listener.ChoiceClickListener;
import com.bat.fractal.catchwordgame7d.presentation.listener.DialogClickListener;
import com.bat.fractal.catchwordgame7d.presentation.widget.adapter.AnswerAdapter;
import com.bat.fractal.catchwordgame7d.presentation.widget.adapter.ChoiceAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter>
        implements MainView, ChoiceClickListener, DialogClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_first_answer)
    RecyclerView firstRVanswer;
    @BindView(R.id.rv_second_answer)
    RecyclerView secondRVanswer;
    @BindView(R.id.rv_choice)
    RecyclerView rvChoice;
    @BindView(R.id.imv_question)
    ImageView imvQuestion;
    @BindView(R.id.tv_timing)
    AppCompatTextView tvTiming;
    @BindView(R.id.tv_level)
    AppCompatTextView tvlevel;
    @BindView(R.id.tv_btn_suggest)
    AppCompatTextView tvBtnSuggest;
    @BindView(R.id.tv_btn_answer)
    AppCompatTextView tvBtnAnswer;
    @Inject
    CatchWordGameDataRepository repository;
    private CountDownTimer countDownTimer;
    private int currentCountdownTime;
    private DialogClickListener dialogClickListener = this;
    private Callback.EmptyCallback imageLoadedCallback = new Callback.EmptyCallback() {
        @Override
        public void onSuccess() {
            initCountdownTimer(getPresenter().timePerQuestion);
        }
    };

    @OnClick(R.id.tv_btn_suggest)
    void clickSuggest(View view) {
        getPresenter().setSuggested(true);
        getPresenter().processSuggest();
        disableButtonSuggest();
    }

    @OnClick(R.id.tv_btn_answer)
    void clickComplete(View view) {
        getPresenter().processComplete();
    }

    @OnClick(R.id.btn_close)
    void clickClose(View view) {
        finish();
    }

    @OnClick(R.id.tv_btn_clear)
    void clickClear(View view) {
        //show suggest button if nessesary
        if (!getPresenter().isSuggested()) {
            enableButtonSuggest();
        }
        //write logic to clear one char
        if (getPresenter().getFirstAnswer().userAnswerLength() > 0) {
            getPresenter().processClear();
        }
        disableButtonAnswer();
    }

    @Override
    public void clearRVanswers(Answer answer1, Answer answer2) {
        refreshRVAnswer(getPresenter().getFirstAdapter(), answer1);
        refreshRVAnswer(getPresenter().getSecondAdapter(), answer2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInjector();
        setPresenter(new MainPresenter(repository));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvBtnAnswer.setBackgroundResource(R.drawable.button_disable);
        disableButtonAnswer();
    }

    private void initCountdownTimer(int time) {
        countDownTimer = new CountDownTimer(time * 1000, Constant.COUNT_DOWN_INTERVAL) {

            public void onTick(long millisUntilFinished) {
                currentCountdownTime = (int) millisUntilFinished / Constant.COUNT_DOWN_INTERVAL;
                if (tvTiming != null) {
                    if (currentCountdownTime < 10) {
                        tvTiming.setText(getString(R.string.time_prefix_1) + currentCountdownTime);
                    } else {
                        tvTiming.setText(getString(R.string.time_prefix_2) + currentCountdownTime);
                    }
                }
            }

            public void onFinish() {
                if (getPresenter().outOfTimeToPlay()) {
                    showOutOfTime();
                } else {
                    dialogHelper.showCustomDialog(DialogType.TIME_OUT, dialogClickListener);
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPresenter().startGame()) {
            refreshLayoutBaseOnFirstQuestion();
        } else {
            refreshLayoutBaseOnQuestion();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    private void initInjector() {
        App.getAppComponent(this).inject(this);
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

    }

    private void initOneLineAnswer(String title) {
        initFirstRVanswer(title.trim());
    }

    private void initFirstRVanswer(String input) {
        firstRVanswer.setHasFixedSize(true);
        // use a linear layout manager
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        firstRVanswer.setLayoutManager(layoutManager);
        getPresenter().initFirstAnswer(input);
        getPresenter().initFirstAdapter(this);
        firstRVanswer.setAdapter(getPresenter().getFirstAdapter());
    }

    private void initSecondRVanswer(String input) {
        secondRVanswer.setHasFixedSize(true);
        // use a linear layout manager
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        secondRVanswer.setLayoutManager(layoutManager);
        getPresenter().initSecondAnswer(input);
        getPresenter().initSecondAdapter(this);
        secondRVanswer.setAdapter(getPresenter().getSecondAdapter());
    }

    private void initTwoLinesAnswer(String title) {
        initFirstRVanswer(getPresenter().getFirstLineFrom(title));
        initSecondRVanswer(getPresenter().getSecondLineFrom(title));
    }

    private void initRVAnswer(Question question) {
        final String questionTitle = question.getTitle();
        if (questionTitle.length() <= Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3) {
            initOneLineAnswer(questionTitle);
            getPresenter().getSecondAnswer().reset();
            refreshRVAnswer(getPresenter().getSecondAdapter(), getPresenter().getSecondAnswer());
        } else {
            initTwoLinesAnswer(questionTitle);
        }
    }

    private void initRVchoice(Question question) {
        rvChoice.setHasFixedSize(true);
        // use a linear layout manager
        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this
                , Constant.RECYCLERVIEW_COLUMN_NUMBER_FOR_J3);
        rvChoice.setLayoutManager(layoutManager);
        final ChoiceAdapter adapter = new ChoiceAdapter(this,
                getPresenter().convertToRVchoiceString(question.getTitle()), this);
        rvChoice.setAdapter(adapter);
    }

    @Override
    public void clickChoice(String string) {
        getPresenter().processChoice(string);
    }

    @Override
    public void enableButtonAnswer() {
        tvBtnAnswer.setClickable(true);
        tvBtnAnswer.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(),
                R.drawable.button_green_selector, null));
    }

    private void disableButtonAnswer() {
        tvBtnAnswer.setClickable(false);
        tvBtnAnswer.setBackgroundResource(R.drawable.button_disable);
    }

    private void enableButtonSuggest() {
        tvBtnSuggest.setClickable(true);
        tvBtnSuggest.setBackgroundResource(R.drawable.button_orange_selector);
    }

    private void disableButtonSuggest() {
        tvBtnSuggest.setClickable(false);
        tvBtnSuggest.setBackgroundResource(R.drawable.button_disable);
    }

    private void refreshRVAnswer(AnswerAdapter answerAdapter, Answer answer) {
        answerAdapter.changeAnswer(answer);
    }

    @Override
    public void clickLeftButton(DialogType dialogType) {
        switch (dialogType) {
            case IN_CORRECT:
                finish();
                break;
            case TIME_OUT:
                finish();
                break;
        }
    }

    @Override
    public void clickRightButton(DialogType dialogType) {
        switch (dialogType) {
            case IN_CORRECT:
                initCountdownTimer(currentCountdownTime);
                enableButtonSuggest();
                getPresenter().clearRVanswers();
                getPresenter().increaseLive();
                break;
            case TIME_OUT:
                initCountdownTimer(getPresenter().timePerQuestion);
                enableButtonSuggest();
                getPresenter().clearRVanswers();
                getPresenter().increaseLive();
                break;
        }
    }

    @Override
    public void showOutOfTime() {
        countDownTimer.cancel();
        dialogHelper.showCustomDialog(DialogType.OUT_OF_TIME_TO_PLAY, this);
    }

    @Override
    public void clickCenterButton(DialogType dialogType) {
        switch (dialogType) {
            case OUT_OF_TIME_TO_PLAY:
                finish();
                break;
            case CONGRATULATION:
                finish();
                break;
            case CORRECT:
                if (getPresenter().questions.size() > 0) {
                    tvlevel.setText(getString(R.string.level) + (getPresenter().getLevel() + 1));
                    refreshLayoutBaseOnQuestion();
                    enableButtonSuggest();
                    getPresenter().setLive(0);
                    getPresenter().setSuggested(false);
                    getPresenter().increaseLevel();
                } else {
                    finish();
                }
                break;
        }
    }

    private void refreshLayoutBaseOnQuestion() {
        Question question = getPresenter().randomQuestionFromQuestions(getPresenter().questions);
        initRVAnswer(question);
        initRVchoice(question);
        //set image
        Picasso.with(this)
                .load(question.getUrlLink())
                .fit()
                .into(imvQuestion, imageLoadedCallback);
    }

    private void refreshLayoutBaseOnFirstQuestion() {
        initRVAnswer(getPresenter().firstQuestion);
        initRVchoice(getPresenter().firstQuestion);
        //set image
        Picasso.with(this)
                .load(getPresenter().firstQuestion.getUrlLink())
                .fit()
                .into(imvQuestion, imageLoadedCallback);
    }

    @Override
    public void refreshRVanswer(AnswerAdapter adapter, Answer answer) {
        refreshRVAnswer(adapter, answer);
    }

    @Override
    public void showCorrect() {
        countDownTimer.cancel();
        dialogHelper.showCustomDialog(DialogType.CORRECT, this);
    }

    @Override
    public void showCongratulation() {
        dialogHelper.showCustomDialog(DialogType.CONGRATULATION, this);
    }

    @Override
    public void showIncorrect() {
        countDownTimer.cancel();
        dialogHelper.showCustomDialog(DialogType.IN_CORRECT, this);
    }

    @Override
    public void relayoutWhenAnswerFilled() {
        enableButtonAnswer();
        disableButtonSuggest();
    }
}
