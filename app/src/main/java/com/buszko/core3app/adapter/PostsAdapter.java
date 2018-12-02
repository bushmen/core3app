package com.buszko.core3app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buszko.core3app.R;
import com.buszko.core3app.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private List<Post> _posts = new ArrayList<>();
    private OnItemClickListener _listener;

    public PostsAdapter(OnItemClickListener _listener) {
        this._listener = _listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item_layout, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.bind(_posts.get(i), _listener);
    }

    @Override
    public int getItemCount() {
        return _posts.size();
    }

    public void loadPosts(List<Post> posts) {
        _posts.clear();
        _posts.addAll(posts);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Post post, View... sharedViews);
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView bodyTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.post_title);
            bodyTextView = itemView.findViewById(R.id.post_body);
        }

        public void bind(Post post, OnItemClickListener listener) {
            titleTextView.setText(post.getTitle());
            bodyTextView.setText(post.getBody());
            itemView.setOnClickListener(view -> listener.onItemClick(post, titleTextView, bodyTextView));
        }
    }
}
