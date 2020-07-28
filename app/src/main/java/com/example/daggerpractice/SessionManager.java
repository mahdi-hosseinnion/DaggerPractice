package com.example.daggerpractice;

import android.util.Log;

import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

@Singleton
public class SessionManager  {
    private static final String TAG = "SessionManager";
    //vars
    private MediatorLiveData<AuthResource<User>> cachedUser=new MediatorLiveData<>();
    @Inject
    public SessionManager() {
    }

    private void  authenticationWithId(final LiveData<AuthResource<User>> source){
        if (cachedUser!=null){
            cachedUser.setValue(AuthResource.loading((User)null));
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);
                }
            });
        }
    }
    private void logOut(){
        Log.d(TAG, "logOut: User logout ...");
        cachedUser.setValue(AuthResource.<User>logout());

    }

    public LiveData<AuthResource<User>> getAuthUser() {
        return cachedUser;
    }
}
