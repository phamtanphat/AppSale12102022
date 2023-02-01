package com.example.appsale12102022.data.remote;

import com.example.appsale12102022.data.remote.dto.UserDTO;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/sign-in")
    Call<UserDTO> signIn(@Body HashMap<String, String> params);
}
