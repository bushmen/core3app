package com.buszko.core3app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.buszko.core3app.model.Post;
import com.buszko.core3app.network.PostsClient;
import com.buszko.core3app.service.PostsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsViewModel extends LoadingViewModel {
    private PostsService _postsService;

    private MutableLiveData<List<Post>> _posts;

    public LiveData<List<Post>> getPosts() {
        if (_posts == null) {
            _posts = new MutableLiveData<>();
            loadPosts();
        }
        return _posts;
    }

    private void loadPosts() {
        _isLoading.setValue(true);
        if (_postsService == null) {
            _postsService = PostsClient.getClient().create(PostsService.class);
        }

        _postsService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                _posts.postValue(response.body());
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                //log error;
                _isLoading.setValue(false);
            }
        });
    }
}
