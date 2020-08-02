package com.example.daggerpractice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.Post;
import com.example.daggerpractice.ui.main.Resource;
import com.example.daggerpractice.util.VerticalSpacingDecorator;
import com.example.daggerpractice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";
    //ui component
    RecyclerView recyclerView;
    //var
    private PostsViewModel viewModel;
    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    PostRecyclerAdapter recyclerAdapter;
    @Inject
    VerticalSpacingDecorator verticalSpacingDecorator;
    @Inject
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        viewModel = new ViewModelProvider(this, providerFactory).get(PostsViewModel.class);
        Toast.makeText(getActivity(), "POSTS FRAGMENT", Toast.LENGTH_SHORT).show();
        initRecycler();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getUserPosts().removeObservers(getViewLifecycleOwner());
        viewModel.getUserPosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case SUCCESS: {
                            Log.d(TAG, "onChanged: data == "+listResource.data);
                            recyclerAdapter.setPosts(listResource.data);
                            break;
                        }
                        case LOADING: {
                            Log.d(TAG, "onChanged: LOADING");
                            break;
                        }
                        case ERROR: {
                            Toast.makeText(getContext(), "" + listResource.message, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }
    private void initRecycler(){
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(verticalSpacingDecorator);
    }
}
