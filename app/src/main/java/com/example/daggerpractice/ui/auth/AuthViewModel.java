package com.example.daggerpractice.ui.auth;

import android.util.Log;

import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private AuthApi authApi;
    @Inject
    public AuthViewModel(AuthApi authApi) {
        Log.d(TAG, "AuthViewModel: viewModel is working yea...");
        if (authApi==null){
            Log.e(TAG, "AuthViewModel: auth api is null" );
        }else
            Log.d(TAG, "AuthViewModel: auth api is not NULL");
    }
}
