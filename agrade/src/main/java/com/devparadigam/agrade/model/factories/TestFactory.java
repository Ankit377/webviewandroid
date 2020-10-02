package com.devparadigam.agrade.model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devparadigam.agrade.model.repositories.TestRepository;
import com.devparadigam.agrade.viewmodels.TestViewModel;

public class TestFactory implements ViewModelProvider.Factory {
    TestRepository testRepository;

    public TestFactory(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TestViewModel.class)) {
            return (T) new TestViewModel(testRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");}
}
