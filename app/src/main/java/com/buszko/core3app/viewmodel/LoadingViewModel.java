package com.buszko.core3app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public abstract class LoadingViewModel extends ViewModel {

    protected MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return _isLoading;
    }
}
