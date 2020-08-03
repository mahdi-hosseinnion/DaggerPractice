package com.example.daggerpractice.di.main;

import android.app.Application;

import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.posts.PostRecyclerAdapter;
import com.example.daggerpractice.util.VerticalSpacingDecorator;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @MainScope
    @Provides
    static MainApi providesMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostRecyclerAdapter providesPostRecyclerAdapter(){
        return new PostRecyclerAdapter();
    }

    @MainScope
    @Provides
    static VerticalSpacingDecorator providesVerticalSpacingDecorator(){
        return new VerticalSpacingDecorator(15);
    }

    @MainScope
    @Provides
    static LinearLayoutManager providesLinearLayoutManager(Application application){
        return new LinearLayoutManager(application);
    }

}
