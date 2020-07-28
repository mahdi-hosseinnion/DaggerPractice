package com.example.daggerpractice.ui.auth;

import android.util.Log;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    //inject
    private AuthApi authApi;
    private SessionManager sessionManager;
    @Inject
    public AuthViewModel(AuthApi authApi,SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager=sessionManager;
    }

    public void authenticateWithUser(final int id) {
        sessionManager.authenticationWithId(queryUserId(id));
    }
    private LiveData<AuthResource<User>>queryUserId(final int id){
        return LiveDataReactiveStreams.fromPublisher(
                authApi
                        .getUserById(id)
                        //instead of calling on error (Error happened)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                return new User(-1, throwable.getMessage(), "", "");
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResource.error(user.getUserName() + "\n Could not authenticate!", (User) null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }

    public LiveData<AuthResource<User>> observerAuthState() {
        return sessionManager.getAuthUser();
    }
}
