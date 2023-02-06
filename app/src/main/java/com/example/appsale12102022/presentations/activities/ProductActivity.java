package com.example.appsale12102022.presentations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appsale12102022.R;
import com.example.appsale12102022.data.model.Product;
import com.example.appsale12102022.data.remote.AppResource;
import com.example.appsale12102022.databinding.ActivityProductBinding;
import com.example.appsale12102022.presentations.adapters.ProductAdapter;
import com.example.appsale12102022.presentations.viewmodels.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    ProductViewModel productViewModel;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initial();
        observerData();

        productViewModel.fetchListProducts();
    }

    private void observerData() {
        productViewModel.getProductResource().observe(this, new Observer<AppResource<List<Product>>>() {
            @Override
            public void onChanged(AppResource<List<Product>> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        productAdapter.updateListProduct(resource.data);
                        break;
                    case LOADING:
                        binding.layoutLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case ERROR:
                        binding.layoutLoading.layoutLoading.setVisibility(View.GONE);
                        Toast.makeText(ProductActivity.this, resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void initial() {
        productViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProductViewModel(ProductActivity.this);
            }
        }).get(ProductViewModel.class);

        productAdapter = new ProductAdapter();
        binding.recyclerViewProduct.setAdapter(productAdapter);
        binding.recyclerViewProduct.setHasFixedSize(true);
    }
}
