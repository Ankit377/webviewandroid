package com.devparadigam.agrade.model.factories;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.devparadigam.agrade.model.repositories.UserRepository;
import com.devparadigam.agrade.viewmodels.UserViewModel;

public class UserFactory implements ViewModelProvider.Factory {

    UserRepository userRepository;

    public UserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");

    }
}
