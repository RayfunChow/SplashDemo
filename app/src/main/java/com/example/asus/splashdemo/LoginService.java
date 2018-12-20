package com.example.asus.splashdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {

    @GET("login")
    Call<Result> login(@Query("username") String username,
                       @Query("password") String password);
}
