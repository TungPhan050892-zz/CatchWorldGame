package com.bat.fractal.catchwordgame7d.domain.usecases;

import com.bat.fractal.catchwordgame7d.datalayer.model.Response;
import com.bat.fractal.catchwordgame7d.domain.repository.CatchWordGameRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by dangm on 03/20/18.
 */

public class GetData extends UseCase<Response, Void> {

    private CatchWordGameRepository repository;

    public GetData(CatchWordGameRepository repository) {
        super();
        this.repository = repository;
    }


    @Override
    Observable<Response> buildUseCaseObservable(Void params) {
        return repository.getQuestion();
    }

    @Override
    Single<Response> buildUseCaseSingle(Void params) {
        return null;
    }

    @Override
    Completable buildUseCaseCompletable(Void params) {
        return null;
    }

}
