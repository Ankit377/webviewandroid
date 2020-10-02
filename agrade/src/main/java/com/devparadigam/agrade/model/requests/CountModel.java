package com.devparadigam.agrade.model.requests;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class CountModel extends BaseObservable {
    private Integer count;

    @Bindable
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        notifyPropertyChanged(BR.count);

    }
}
