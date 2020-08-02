package com.example.daggerpractice.ui.main.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daggerpractice.R;
import com.example.daggerpractice.models.Post;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder> {
    private List<Post> mPosts = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((PostRecyclerAdapter.ViewHolder)holder).onBind(mPosts.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPosts == null)
            return 0;
        else
            return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView post_item_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_item_title = itemView.findViewById(R.id.post_item_title);

        }

        public void onBind(Post post) {
            post_item_title.setText(post.getTitle());
        }
    }
    public void setPosts(List<Post>postList){
        this.mPosts=postList;
        notifyDataSetChanged();
    }

}
