package com.dicoding.githubapp.util

import android.view.View
import com.dicoding.githubapp.BuildConfig

object Utils {
    const val API_KEY_GITHUB = BuildConfig.TOKEN_API_GITHUB
    const val BASE_URL = BuildConfig.BASE_URL

    fun showLoading(view: View, isLoading: Boolean) =
        if (isLoading) view.visibility = View.VISIBLE
        else view.visibility = View.INVISIBLE

}