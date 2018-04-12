package com.bat.fractal.catchwordgame7d.presentation.start;

import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.model.Settings;
import com.bat.fractal.catchwordgame7d.presentation.base.BaseView;

import java.util.List;

/**
 * Created by admin on 3/27/18.
 */

public interface StartView extends BaseView{


    void initSetting(Settings settings);

    void initTheRestOfQuestions(List<Question> questions);

    void initFirstQuestion(Question question);

}
