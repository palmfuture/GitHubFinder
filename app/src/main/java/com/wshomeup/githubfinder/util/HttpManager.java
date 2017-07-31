package com.wshomeup.githubfinder.util;

import com.wshomeup.githubfinder.service.GitHubService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private GitHubService service;

    private HttpManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")   // MUST end url with '/'
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);
    }

    public GitHubService getService() {
        return service;
    }
}
