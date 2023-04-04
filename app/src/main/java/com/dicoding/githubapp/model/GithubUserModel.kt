package com.dicoding.githubapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUserModel(
    val login: String,
    val imgUrl: String
) : Parcelable