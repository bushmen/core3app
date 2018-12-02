package com.buszko.core3app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.buszko.core3app.model.Comment;
import com.buszko.core3app.model.Post;
import com.buszko.core3app.model.User;
import com.buszko.core3app.network.PostsClient;
import com.buszko.core3app.service.PostsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailsViewModel extends LoadingViewModel {
    private MutableLiveData<List<Comment>> _comments;
    private MutableLiveData<User> _user;
    private PostsService _postsService;

    public LiveData<List<Comment>> getCommentsForPost(String id) {
        if (_comments == null) {
            _comments = new MutableLiveData<>();
            loadComments(id);
        }
        return _comments;
    }

    public LiveData<User> getUser(String id) {
        if (_user == null) {
            _user = new MutableLiveData<>();
            loadUser(id);
        }
        return _user;
    }

    private void loadComments(String id) {
        _isLoading.setValue(true);
        if (_postsService == null) {
            _postsService = PostsClient.getClient().create(PostsService.class);
        }

        _postsService.getComments(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                _comments.postValue(response.body());
                _isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                //log error;
                _isLoading.setValue(false);
            }
        });
    }

    private void loadUser(String id) {
        if (_postsService == null) {
            _postsService = PostsClient.getClient().create(PostsService.class);
        }

        _postsService.getUser(id).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    _user.setValue(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                //log error
            }
        });
    }
}
