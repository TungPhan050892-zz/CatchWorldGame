package com.bat.fractal.catchwordgame7d.presentation.main;

import com.bat.fractal.catchwordgame7d.datalayer.model.Answer;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.model.Settings;
import com.bat.fractal.catchwordgame7d.presentation.base.BaseView;
import com.bat.fractal.catchwordgame7d.presentation.widget.adapter.AnswerAdapter;

import java.util.List;

/**
 * Created by dangm on 03/19/18.
 */

interface MainView extends BaseView {

    void refreshRVanswer(AnswerAdapter adapter, Answer answer);

    void showCorrect();

    void showCongratulation();

    void showOutOfTime();

    void showIncorrect();

    void clearRVanswers(Answer answer1, Answer answer2);

    void relayoutWhenAnswerFilled();

    void enableButtonAnswer();

}
