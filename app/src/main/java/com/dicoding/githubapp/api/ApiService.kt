package com.dicoding.githubapp.api

import com.dicoding.githubapp.model.*
import com.dicoding.githubapp.util.Utils
import com.google.gson.JsonObject
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

    @GET("users/{login}")
    @Headers("Authorization: token ${Utils.API_KEY_GITHUB}")
    fun getUserDetail(
        @Path("login") login: String
    ): Call<DetailUser>

    @GET("users/{login}/followers")
    @Headers("Authorization: token ${Utils.API_KEY_GITHUB}")
    fun getUserFollowers(
        @Path("login") login: String
    ): Call<List<FollowersGithubModelItem>>

    @GET("users/{login}/following")
    @Headers("Authorization: token ${Utils.API_KEY_GITHUB}")
    fun getUserFollowing(
        @Path("login") login: String
    ): Call<List<FollowingGithubModelItem>>

}