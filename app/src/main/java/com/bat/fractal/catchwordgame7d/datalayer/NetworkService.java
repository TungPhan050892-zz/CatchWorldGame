package com.bat.fractal.catchwordgame7d.datalayer;

import com.bat.fractal.catchwordgame7d.BuildConfig;
import com.bat.fractal.catchwordgame7d.datalayer.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by tung phan on 04-18-2017.
 * implement call to the interface here.
 */
public interface NetworkService {

    String BASE_URL = BuildConfig.CATCH_WORLD_GAME;

    @GET("data")
    Observable<Response> getQuestion();

}
