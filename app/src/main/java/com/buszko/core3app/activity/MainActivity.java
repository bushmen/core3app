package com.buszko.core3app.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.buszko.core3app.R;
import com.buszko.core3app.adapter.PostsAdapter;
import com.buszko.core3app.model.Post;
import com.buszko.core3app.viewmodel.PostsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends BaseActivity implements PostsAdapter.OnItemClickListener {

    private RecyclerView _recyclerView;
    private ProgressBar _progressBar;

    public static final String POST_ITEM_EXTRA = "post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _progressBar = findViewById(R.id.progress_bar);
        _recyclerView = findViewById(R.id.posts_recycler_view);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(layout);

        PostsAdapter adapter = new PostsAdapter(this);
        _recyclerView.setAdapter(adapter);
        _recyclerView.addItemDecoration(new DividerItemDecoration(this, layout.getOrientation()));

        PostsViewModel postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsViewModel.getIsLoading().observe(this, this::showLoading);
        postsViewModel.getPosts().observe(this, adapter::loadPosts);
    }

    @Override
    public void onItemClick(Post post, View... sharedViews) {
        logEvent(post);
        Intent intent = new Intent(this, PostDetailsActivity.class);
        intent.putExtra(POST_ITEM_EXTRA, post);

        Pair[] sharedElements = prepareSharedViews(sharedViews);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedElements);

        startActivity(intent, options.toBundle());
    }

    private void showLoading(boolean isLoading) {
        _progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        _recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private Pair[] prepareSharedViews(View... sharedViews) {
        Pair[] views = new Pair[sharedViews.length];
        for (int i = 0; i < sharedViews.length; i++){
            View sharedView = sharedViews[i];
            views[i] = new Pair<>(sharedView, ViewCompat.getTransitionName(sharedView));
        }

        return views;
    }

    private void logEvent(Post post) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, post.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, post.getTitle());
        _firebaseAnalytics.logEvent("post_selected", bundle);
    }
}
