package com.dicoding.githubapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    @field:SerializedName("login")
    val login: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("followers")
    val followers: String?,
    @field:SerializedName("following")
    val following: String?,

) : Parcelable