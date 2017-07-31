package com.wshomeup.githubfinder.service;

import com.wshomeup.githubfinder.model.GitHubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/{user}")
    Call<GitHubUser> loadUser(@Path("user") String user);
}
