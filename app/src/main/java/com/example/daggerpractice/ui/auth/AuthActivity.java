package com.example.daggerpractice.ui.auth;


import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class AuthActivity extends DaggerAppCompatActivity {
    private static final String TAG = "AuthActivity";
    private AuthViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    RequestManager glideInstance;
    @Inject
    Drawable logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        viewModel= ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();
    }
    private void setLogo(){
        glideInstance.load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

}
