package com.example.appsale12102022.data.remote;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/sign-in")
    Call<String> signIn(@Body RequestBody params);
}
