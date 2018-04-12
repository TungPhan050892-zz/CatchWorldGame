package com.bat.fractal.catchwordgame7d.domain.repository;


import com.bat.fractal.catchwordgame7d.datalayer.model.Response;

import io.reactivex.Observable;

/**
 * Created by tung.phan on 5/31/2017.
 */

public interface CatchWordGameRepository {

    Observable<Response> getQuestion();


//    Observable<LoginResponse> login(String username, String password);
//
//    Observable<ListOrderResponse> getListOrder(String token);
//
//    Observable<ReasonResponse> getReasonCancel(String token);
//
//    Observable<BaseResponse> cancel(String token, int orderId, int reasonId, String reasonText);
//
//    Observable<BaseResponse> update(String token, int orderId, String name, String address,
//                                    String relationship, String phone, File image,
//                                    double lat, double lng);

}
