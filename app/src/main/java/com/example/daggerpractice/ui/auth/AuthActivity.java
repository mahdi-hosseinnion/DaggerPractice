package com.example.daggerpractice.ui.auth;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.daggerpractice.R;
import com.example.daggerpractice.models.User;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";
    //ui component
    EditText edt_userId;
    //vars
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
        edt_userId=findViewById(R.id.user_id_input);
        findViewById(R.id.login_button).setOnClickListener(this);
        viewModel= new ViewModelProvider(this,providerFactory).get(AuthViewModel.class);
        setLogo();
        subscribeToObservers();
    }
    private void subscribeToObservers(){
        viewModel.observerUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user!=null){
                    Log.d(TAG, "onChanged: "+user.toString());
                }
            }
        });
    }
    private void setLogo(){
        glideInstance.load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(edt_userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithUser(Integer.parseInt(edt_userId.getText().toString()));
    }
}
