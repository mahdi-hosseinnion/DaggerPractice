package com.example.daggerpractice.ui.auth;

import android.util.Log;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private AuthApi authApi;
    private MediatorLiveData<User> authUser=new MediatorLiveData<>();
    @Inject
    public AuthViewModel(AuthApi authApi) {
        Log.d(TAG, "AuthViewModel: viewModel is working yea...");
        this.authApi=authApi;
    }
    public void authenticateWithUser(final int id){
        final LiveData<User> source= LiveDataReactiveStreams.fromPublisher(
                authApi
                .getUserById(id)
                .subscribeOn(Schedulers.io())
        );
        authUser.addSource(source, new androidx.lifecycle.Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }


    public LiveData<User> observerUser() {
        return authUser;
    }
}
