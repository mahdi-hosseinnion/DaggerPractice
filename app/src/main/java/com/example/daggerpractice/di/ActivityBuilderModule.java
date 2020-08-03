package com.example.daggerpractice.di;

import com.example.daggerpractice.di.auth.AuthModule;
import com.example.daggerpractice.di.auth.AuthScope;
import com.example.daggerpractice.di.auth.AuthViewModelModule;
import com.example.daggerpractice.di.main.MainFragmentBuilderModule;
import com.example.daggerpractice.di.main.MainModule;
import com.example.daggerpractice.di.main.MainScope;
import com.example.daggerpractice.di.main.MainViewModelsModule;
import com.example.daggerpractice.ui.auth.AuthActivity;
import com.example.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    //AuthActivity
    @AuthScope
    @ContributesAndroidInjector(
            modules =
                    {
                            AuthViewModelModule.class,
                            AuthModule.class
                    }
    )
    abstract AuthActivity contributeAuthActivity();

    //MainActivity
    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class,
                    MainViewModelsModule.class,
                    MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity(

    );


}
