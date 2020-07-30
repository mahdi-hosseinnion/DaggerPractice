package com.example.daggerpractice.ui.main.profile;

import android.util.Log;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";
    private SessionManager sessionManager;
    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager=sessionManager;
        Log.d(TAG, "ProfileViewModel: viewModel is working yea....");
    }
    public LiveData<AuthResource<User>> getAuthenticateUser(){
        return sessionManager.getAuthUser();
    }
}
