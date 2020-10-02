package com.devparadigam.agrade.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devparadigam.agrade.model.repositories.ProductRepository;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.ItemCategoryModel;

import java.util.List;

public class ProductViewModel extends ViewModel {

    ProductRepository productRepository;
    LiveData<Resource<List<ItemCategoryModel>>> productLiveData;

    public ProductViewModel(ProductRepository productRepository) {
        this.productRepository = productRepository;

        productLiveData = new MediatorLiveData<>();
        ((MediatorLiveData<Resource<List<ItemCategoryModel>>>) productLiveData).postValue(new Resource<List<ItemCategoryModel>>(Status.IDEAL,null,""));

    }

    public MutableLiveData<Resource<List<ItemCategoryModel>>> getCategory(String token){
        return productRepository.getCategory(token);
    }
}
