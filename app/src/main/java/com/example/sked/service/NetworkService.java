package com.example.sked.service;

import com.example.sked.api.InstituteApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;

    private static final String BASE_URL = "https://server-sked.herokuapp.com";
    private Retrofit retrofit;

    private NetworkService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public InstituteApi getInstitutionApi() {
        return retrofit.create(InstituteApi.class);
    }
}
