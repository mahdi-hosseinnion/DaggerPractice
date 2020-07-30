package com.example.daggerpractice.ui.main.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.auth.AuthResource;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";
    //ui component
    TextView txt_email,txt_userName,txt_webSite;
    //var
    ProfileViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: is running...");
        txt_email=view.findViewById(R.id.email);
        txt_userName=view.findViewById(R.id.username);
        txt_webSite=view.findViewById(R.id.website);
        viewModel = new ViewModelProvider(this, providerFactory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getAuthenticateUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticateUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case AUTHENTICATED: {
                            setUserDetails(userAuthResource.data);
                            break;
                        }
                        case ERROR: {
                            setErrorDetails(userAuthResource.message);
                            break;
                        }

                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        if (TextUtils.isEmpty(message)){
            txt_email.setText("ERROR");
            txt_webSite.setText("ERROR");
            txt_userName.setText("ERROR");
        }else {
            txt_email.setText(message);
            txt_webSite.setText("ERROR");
            txt_userName.setText("ERROR");
        }
    }

    private void setUserDetails(User user) {
        if (user!=null){
            txt_email.setText(user.getEmail());
            txt_userName.setText(user.getUserName()+" #"+user.getId());
            txt_webSite.setText(user.getWebsite());
        }
    }
}
