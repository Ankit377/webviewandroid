package com.devparadigam.agrade.model.factories;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devparadigam.agrade.model.repositories.ProductRepository;
import com.devparadigam.agrade.viewmodels.ProductViewModel;

public class ProductFactory implements ViewModelProvider.Factory {

    ProductRepository productRepository;

    public ProductFactory(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(productRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");

    }
}
