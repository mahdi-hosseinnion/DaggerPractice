package com.example.daggerpractice;


import dagger.android.support.DaggerAppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

public class AuthActivity extends DaggerAppCompatActivity {
    private static final String TAG = "AuthActivity";
    @Inject
    RequestManager glideInstance;
    @Inject
    Drawable logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        setLogo();
    }
    private void setLogo(){
        glideInstance.load(logo)
                .into((ImageView)findViewById(R.id.login_logo));
    }

}
