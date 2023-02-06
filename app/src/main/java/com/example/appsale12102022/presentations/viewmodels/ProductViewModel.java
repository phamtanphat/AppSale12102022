package com.example.appsale12102022.presentations.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appsale12102022.common.AppConstant;
import com.example.appsale12102022.data.local.SharePrefApp;
import com.example.appsale12102022.data.model.Product;
import com.example.appsale12102022.data.model.User;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.data.remote.dto.ProductDTO;
import com.example.appsale12102022.data.remote.dto.UserDTO;
import com.example.appsale12102022.data.repositories.AuthenticationRepository;
import com.example.appsale12102022.data.repositories.ProductRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pphat on 2/6/2023.
 */
public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private MutableLiveData<AppResource<List<Product>>> productResource = new MutableLiveData<>();

    public ProductViewModel(Context context) {
        productRepository = new ProductRepository(context);
    }

    public LiveData<AppResource<List<Product>>> getProductResource() {
        return productResource;
    }

    public void fetchListProducts() {
        productResource.setValue(new AppResource.Loading<>(null));
        productRepository.getListProducts().enqueue(new Callback<AppResource<List<ProductDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<ProductDTO>>> call, Response<AppResource<List<ProductDTO>>> response) {
                if (response.isSuccessful()) {
                    List<ProductDTO> productDTOList = response.body().data;
                    List<Product> productList = new ArrayList<>();

                    for (ProductDTO productDTO: productDTOList) {
                        productList.add(new Product(
                                productDTO.getId(),
                                productDTO.getName(),
                                productDTO.getAddress(),
                                productDTO.getPrice(),
                                productDTO.getImg(),
                                productDTO.getQuantity(),
                                productDTO.getGallery()
                        ));
                    }

                    productResource.setValue(new AppResource.Success<>(productList));
                } else {
                    if (response.errorBody() == null) return;
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        productResource.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<ProductDTO>>> call, Throwable t) {
                productResource.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
