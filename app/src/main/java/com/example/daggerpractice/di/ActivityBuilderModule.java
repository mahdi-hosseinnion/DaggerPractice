package com.example.daggerpractice.di;

import com.example.daggerpractice.AuthActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();

    @Provides
    static String someString(){
        return "something for just fun";
    }
}
