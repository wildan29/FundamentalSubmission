package com.dicoding.githubapp.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    @field: SerializedName("id")
    val id: Int = 0,
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @field:SerializedName("followers")
    val followers: String? = null,
    @field:SerializedName("following")
    val following: String? = null,
    @field:SerializedName("html_url")
    val html: String? = null
) : Parcelable