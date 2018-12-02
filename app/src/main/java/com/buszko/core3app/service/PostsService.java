package com.buszko.core3app.service;

import com.buszko.core3app.model.Comment;
import com.buszko.core3app.model.Post;
import com.buszko.core3app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostsService {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/comments?")
    Call<List<Comment>> getComments(@Query("postId") String id);

    @GET("/users?")
    Call<List<User>> getUser(@Query("id") String id);
}
