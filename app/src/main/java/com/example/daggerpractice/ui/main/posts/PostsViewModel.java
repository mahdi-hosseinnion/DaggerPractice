package com.example.daggerpractice.ui.main.posts;

import android.util.Log;

import com.example.daggerpractice.SessionManager;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.network.main.MainApi;
import com.example.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";
    //var
    private final SessionManager sessionManager;
    private final MainApi mainApi;
    private MediatorLiveData<Resource<List<Post>>> mUserPosts;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;
        Log.d(TAG, "PostsViewModel: Post ViewModel is working ....");
    }

    public LiveData<Resource<List<Post>>> getUserPosts() {
        if (mUserPosts == null)
            mUserPosts = new MediatorLiveData<>();
        mUserPosts.setValue(Resource.loading((List<Post>) null));
        final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                mainApi
                        .getPostFromUser(sessionManager.getAuthUser().getValue().data.getId())
                        .onErrorReturn(new Function<Throwable, List<Post>>() {
                            @Override
                            public List<Post> apply(Throwable throwable) throws Exception {
                                Log.d(TAG, "apply: cannot get the post .");
                                Post post = new Post();
                                post.setId(-1);
                                post.setTitle(throwable.getMessage());
                                List<Post> posts = new ArrayList<>();
                                posts.add(post);
                                return posts;
                            }
                        })
                        .map(new Function<List<Post>, Resource<List<Post>>>() {
                            @Override
                            public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                                if (posts.size() > 0) {
                                    if (posts.get(0).getId() == -1) {
                                        return Resource.error(posts.get(0).getTitle() != null ?
                                                        posts.get(0).getTitle() :
                                                        "something went wrong",
                                                null);
                                    }
                                }
                                return Resource.success(posts);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
        mUserPosts.addSource(source, new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                mUserPosts.setValue(listResource);
                mUserPosts.removeSource(source);
            }
        });
        return mUserPosts;
    }

}
