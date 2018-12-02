package com.buszko.core3app.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buszko.core3app.R;
import com.buszko.core3app.adapter.CommentsAdapter;
import com.buszko.core3app.model.Post;
import com.buszko.core3app.model.User;
import com.buszko.core3app.viewmodel.PostDetailsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

public class PostDetailsActivity extends BaseActivity {

    private TextView _titleTextView;
    private TextView _bodyTextView;
    private TextView _nameTextView;
    private RecyclerView _recyclerView;
    private ProgressBar _progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Bundle extras = getIntent().getExtras();
        Post post = extras.getParcelable(MainActivity.POST_ITEM_EXTRA);

        _titleTextView = findViewById(R.id.post_title);
        _bodyTextView = findViewById(R.id.post_body);
        _nameTextView = findViewById(R.id.post_author_name);
        _recyclerView = findViewById(R.id.comments_recycler_view);
        _progressBar = findViewById(R.id.progress_bar);

        _titleTextView.setText(post.getTitle());
        _bodyTextView.setText(post.getBody());

        CommentsAdapter adapter = new CommentsAdapter();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(layout);
        _recyclerView.setAdapter(adapter);
        _recyclerView.addItemDecoration(new DividerItemDecoration(this, layout.getOrientation()));

        PostDetailsViewModel viewModel = ViewModelProviders.of(this).get(PostDetailsViewModel.class);
        viewModel.getIsLoading().observe(this, this::showLoading);
        viewModel.getUser(post.getUserId()).observe(this, user -> _nameTextView.setText(user.getName()));
        viewModel.getCommentsForPost(post.getId()).observe(this, adapter::loadComments);

        _nameTextView.setOnClickListener(view -> onAuthorNameClick(viewModel.getUser(post.getUserId()).getValue()));
    }

    private void showLoading(boolean isLoading) {
        _progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        _recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    public void onAuthorNameClick(User user) {
        logEvent(user);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + user.getEmail());
        intent.setData(data);
        startActivity(intent);
    }

    private void logEvent(User user) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, user.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, user.getName());
        bundle.putString("user_mail", user.getName());
        _firebaseAnalytics.logEvent("mailing_user", bundle);
    }
}
