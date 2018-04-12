package com.bat.fractal.catchwordgame7d.datalayer.repository;


import com.bat.fractal.catchwordgame7d.datalayer.NetworkService;
import com.bat.fractal.catchwordgame7d.datalayer.model.Response;
import com.bat.fractal.catchwordgame7d.domain.repository.CatchWordGameRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by tung.phan on 5/31/2017.
 */

public class CatchWordGameDataRepository implements CatchWordGameRepository {

    private NetworkService networkService;

    @Inject
    public CatchWordGameDataRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Observable<Response> getQuestion() {
        return networkService.getQuestion();
    }


//    @Override
//    public Observable<LoginResponse> login(String username, String password) {
//        return networkService.login(username, password);
//    }
//
//    @Override
//    public Observable<ListOrderResponse> getListOrder(String token) {
//        return networkService.getListOrder(token);
//    }
//
//    @Override
//    public Observable<ReasonResponse> getReasonCancel(String token) {
//        return networkService.getCancelReason(token);
//    }
//
//    @Override
//    public Observable<BaseResponse> cancel(String token, int orderId, int reasonId, String reasonText) {
//        return networkService.cancel(token, orderId, reasonId, reasonText);
//    }
//
//    @Override
//    public Observable<BaseResponse> update(String token, int orderId, String name, String address,
//                                           String relationship, String phone, File image,
//                                           double lat, double lng) {
//        MultipartBody.Part imagePart = null;
//        if (image != null) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image);
//            imagePart = MultipartBody.Part.createFormData("image", image.getName(), requestFile);
//        }
//        RequestBody tokenPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(token));
//        RequestBody orderIdPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(orderId));
//        RequestBody namePart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(name));
//        RequestBody addressPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(address));
//        RequestBody relationshipPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(relationship));
//        RequestBody phonePart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(phone));
//        RequestBody latPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(lat));
//        RequestBody lngPart = RequestBody.create(
//                MultipartBody.FORM, String.valueOf(lng));
//        return networkService.update(tokenPart, orderIdPart, namePart, addressPart, relationshipPart,
//                phonePart, imagePart, latPart, lngPart);
//    }

}
