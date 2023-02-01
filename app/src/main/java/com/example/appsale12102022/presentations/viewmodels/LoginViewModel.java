package com.example.appsale12102022.presentations.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.appsale12102022.data.remote.dto.UserDTO;
import com.example.appsale12102022.data.repositories.AuthenticationRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 2/1/2023.
 */
public class LoginViewModel extends ViewModel {
    private AuthenticationRepository authenticationRepository;

    public LoginViewModel(Context context) {
        authenticationRepository = new AuthenticationRepository(context);
    }

    public void signIn() {
        authenticationRepository.signIn("demo1210@gmail.com", "123456789")
                .enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        if (response.isSuccessful()) {
                            UserDTO userDTO = response.body();
                            Log.d("BBB", userDTO.getData().getEmail());
                        } else {
                            if (response.errorBody() == null) return;
                            try {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.getString("message");
                                Log.d("BBB","Lỗi trong onresponse" + message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        Log.d("BBB", "Lỗi trong onFailure " + t.getMessage());
                    }
                });
    }
}
