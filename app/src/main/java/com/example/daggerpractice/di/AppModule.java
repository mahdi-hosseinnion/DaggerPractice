package com.example.daggerpractice.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    static String someString(){
        return "something for just fun";
    }
    @Provides
    static boolean isAppNull(Application application){
        return application==null;
    }
}
