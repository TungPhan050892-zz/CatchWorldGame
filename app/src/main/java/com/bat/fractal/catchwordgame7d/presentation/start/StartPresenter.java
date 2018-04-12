package com.bat.fractal.catchwordgame7d.presentation.start;

import com.bat.fractal.catchwordgame7d.common.ErrorCode;
import com.bat.fractal.catchwordgame7d.datalayer.model.Question;
import com.bat.fractal.catchwordgame7d.datalayer.model.Response;
import com.bat.fractal.catchwordgame7d.datalayer.repository.CatchWordGameDataRepository;
import com.bat.fractal.catchwordgame7d.domain.usecases.GetData;
import com.bat.fractal.catchwordgame7d.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 3/27/18.
 */

public class StartPresenter extends BasePresenter<StartView> {

    private GetData getData;

    @Inject
    public StartPresenter(CatchWordGameDataRepository repository) {
        getData = new GetData(repository);
    }

    void getData() {
        disposable.add(getData.param(null)
                .on(Schedulers.io(), AndroidSchedulers.mainThread())
                .execute(new GetDataObserver()));
    }

    private class GetDataObserver extends DisposableObserver<Response> {

        @Override
        public void onNext(Response response) {
            if (response.getErrorCode() == 0 || response.getData() == null) {
                getView().initSetting(response.getData().getSettings());
                final List<Question> questions = response.getData().getQuestions();
                getView().initFirstQuestion(getFirstQuestion(questions));
                questions.remove(0);
                getView().initTheRestOfQuestions(questions);
            } else {
                getView().showErrorDialog(ErrorCode.REPONSE_FAILED);
            }
        }

        private Question getFirstQuestion(List<Question> questions) {
            return questions.get(0);
        }

        @Override
        public void onError(Throwable e) {
            getView().hideLoading();
            getView().showErrorDialog(ErrorCode.REPONSE_FAILED);
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }
}
