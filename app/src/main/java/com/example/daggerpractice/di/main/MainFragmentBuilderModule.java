package com.example.daggerpractice.di.main;

import com.example.daggerpractice.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuilderModule {
    private static final String TAG = "MainFragmentBuilderModule";
    @ContributesAndroidInjector
    abstract ProfileFragment  contributesProfileFragment();
}
