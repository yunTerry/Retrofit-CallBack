package com.terry.retrofit_callback.http;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * *
 * name     RestAPI
 * Creater  Terry
 * time     2017/6/21
 * *
 **/

public interface RestAPI {

    @GET("/userinfo")
    Observable<BaseModel<User>> getRxUser();

    @GET("/userinfo")
    Call<BaseModel<User>> getUser();

}
