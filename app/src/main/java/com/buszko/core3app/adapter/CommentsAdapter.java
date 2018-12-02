package com.buszko.core3app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buszko.core3app.R;
import com.buszko.core3app.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comment> _comments = new ArrayList<>();

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item_layout, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.bind(_comments.get(i));
    }

    @Override
    public int getItemCount() {
        return _comments.size();
    }

    public void loadComments(List<Comment> comments) {
        _comments.clear();
        _comments.addAll(comments);
        notifyDataSetChanged();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView _commentName;
        private TextView _commentEmail;
        private TextView _commentBody;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            _commentName = itemView.findViewById(R.id.comment_name);
            _commentEmail = itemView.findViewById(R.id.comment_email);
            _commentBody = itemView.findViewById(R.id.comment_body);
        }

        public void bind(Comment comment) {
            _commentName.setText(comment.getName());
            _commentEmail.setText(comment.getEmail());
            _commentBody.setText(comment.getBody());

        }
    }
}
