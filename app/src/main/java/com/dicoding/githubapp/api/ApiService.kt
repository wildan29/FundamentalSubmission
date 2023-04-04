package com.dicoding.githubapp.api

import com.dicoding.githubapp.model.GithubResponeApi
import com.dicoding.githubapp.model.GithubResponse
import com.dicoding.githubapp.util.Utils
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    @Headers("Authorization: token ${Utils.API_KEY_GITHUB}")
    fun getAllUser(): Call<GithubResponeApi>

    @GET("search/users")
    @Headers("Authorization: token ${Utils.API_KEY_GITHUB}")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubResponse>
}