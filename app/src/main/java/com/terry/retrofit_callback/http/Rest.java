package com.terry.retrofit_callback.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * *
 * name     Rest
 * Creater  Terry
 * time     2017/6/21
 * *
 **/

public class Rest {

    private static RestAPI restAPI;

    public static RestAPI getRestApi() {
        if (restAPI == null) {
            restAPI = new Retrofit.Builder()
                    .baseUrl("http://10.45.54.128:2017")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(RestAPI.class);
        }
        return restAPI;

    }

}
