package com.example.appsale12102022.presentations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appsale12102022.R;
import com.example.appsale12102022.data.model.User;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.dto.UserDTO;
import com.example.appsale12102022.databinding.ActivityLoginBinding;
import com.example.appsale12102022.presentations.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(LoginActivity.this);
            }
        }).get(LoginViewModel.class);

        loginViewModel.getUserResource().observe(this, new Observer<AppResource<User>>() {
            @Override
            public void onChanged(AppResource<User> userDTOAppResource) {
                switch (userDTOAppResource.status) {
                    case SUCCESS:
                        Log.d("BBB", "onSuccess " + userDTOAppResource.data.getEmail());
                        break;
                    case LOADING:
                        Log.d("BBB", "onLoading ");
                        break;
                    case ERROR:
                        Log.d("BBB", "onFail " + userDTOAppResource.message);
                        break;
                }
            }
        });

        loginViewModel.signIn("demo1210@gmail.com", "123456789");
    }
}
