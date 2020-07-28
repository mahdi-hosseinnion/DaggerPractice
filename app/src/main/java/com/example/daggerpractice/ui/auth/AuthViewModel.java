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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    private AuthApi authApi;
    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    @Inject
    public AuthViewModel(AuthApi authApi) {
        Log.d(TAG, "AuthViewModel: viewModel is working yea...");
        this.authApi = authApi;
    }

    public void authenticateWithUser(final int id) {
        authUser.setValue(AuthResource.loading((User) null));
        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
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
        authUser.addSource(source, new androidx.lifecycle.Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }


    public LiveData<AuthResource<User>> observerUser() {
        return authUser;
    }
}
