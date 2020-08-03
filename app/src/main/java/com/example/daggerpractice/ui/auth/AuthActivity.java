package com.example.daggerpractice.ui.auth;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.ui.main.MainActivity;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";
    //ui component
    EditText edt_userId;
    ProgressBar progress_bar;
    //vars
    private AuthViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    RequestManager glideInstance;
    @Inject
    Drawable logo;
    @Inject
    @Named("app_user")
    User user1;

    @Inject
    @Named("auth_user")
    User user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        edt_userId = findViewById(R.id.user_id_input);
        progress_bar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);
        viewModel = new ViewModelProvider(this, providerFactory).get(AuthViewModel.class);
        setLogo();
        subscribeToObservers();
        Log.d(TAG, "onCreate: 1: " + user1);
        Log.d(TAG, "onCreate: 2: " + user2);
    }

    private void subscribeToObservers() {
        viewModel.observerAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: LOGIN SUCCESS email: " + userAuthResource.data.getEmail());
                            onLoginSuccess();
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this, userAuthResource.message +
                                    "\n Did you enter a number between 1 and 10 ?", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible)
            progress_bar.setVisibility(View.VISIBLE);
        else
            progress_bar.setVisibility(View.GONE);
    }

    private void setLogo() {
        glideInstance.load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(edt_userId.getText().toString())) {
            return;
        }
        viewModel.authenticateWithUser(Integer.parseInt(edt_userId.getText().toString()));
    }
}
