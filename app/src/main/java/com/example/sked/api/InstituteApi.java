package com.example.sked.api;

import com.example.sked.domain.Institute;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InstituteApi {

    @GET("/institute/get-institutions")
    public Call<List<Institute>> getInstitutes();
}
