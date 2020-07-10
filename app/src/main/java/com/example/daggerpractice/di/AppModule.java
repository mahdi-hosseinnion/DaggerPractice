package com.example.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.daggerpractice.R;

import javax.inject.Singleton;

import androidx.core.content.ContextCompat;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    static RequestOptions provideRequestManager() {
        return RequestOptions
                .placeholderOf(R.drawable.white_background)
                .error(R.drawable.logo);
    }

    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }
    @Provides
    static Drawable provideDrawable(Application application){
        return ContextCompat.getDrawable(application,R.drawable.logo);
    }


}
