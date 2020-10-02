package com.devparadigam.agrade.model.repositories;

import androidx.lifecycle.MutableLiveData;

import com.devparadigam.agrade.apiservices.ApiServices;
import com.devparadigam.agrade.apiservices.JsonArrayResponse;
import com.devparadigam.agrade.model.requests.Resource;
import com.devparadigam.agrade.model.requests.Status;
import com.devparadigam.agrade.model.response.ItemCategoryModel;


import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProductRepository {
    private ApiServices mApiServices;
    private Disposable disposable;


    public ProductRepository(ApiServices mApiServices) {
        this.mApiServices = mApiServices;

    }

    public MutableLiveData<Resource<List<ItemCategoryModel>>> getCategory(String token){
        final MutableLiveData<Resource<List<ItemCategoryModel>>> productData = new MutableLiveData<>();
        mApiServices.getCategory(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonArrayResponse<ItemCategoryModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        productData.setValue(new Resource<List<ItemCategoryModel>>(Status.LOADING,null,"Loading"));
                    }

                    @Override
                    public void onSuccess(JsonArrayResponse<ItemCategoryModel> itemCategoryModelJsonObjectResponse) {

                        if (itemCategoryModelJsonObjectResponse!=null) {
                            if (itemCategoryModelJsonObjectResponse.getSuccess()) {
                                if (itemCategoryModelJsonObjectResponse.list != null) {
                                    productData.setValue(new Resource<List<ItemCategoryModel>>(Status.SUCCESS, itemCategoryModelJsonObjectResponse.list, "" + itemCategoryModelJsonObjectResponse.getMessage()));
                                } else {
                                    productData.setValue(new Resource<List<ItemCategoryModel>>(Status.ERROR, null, "" + itemCategoryModelJsonObjectResponse.getMessage()));
                                }
                            }else {
                                productData.setValue(new Resource<List<ItemCategoryModel>>(Status.ERROR, null, "" + itemCategoryModelJsonObjectResponse.getMessage()));
                            }
                        }else {
                            productData.setValue(new Resource<List<ItemCategoryModel>>(Status.ERROR,null, "Something went wrong"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        productData.setValue(new Resource<List<ItemCategoryModel>>(Status.ERROR, null, ""+e.getMessage()));
                    }
                });
        return productData;
    }
}
