package com.dicoding.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.database.FavoriteGithubUser
import com.dicoding.githubapp.repository.FavoriteGithubUserRepository

class FavoriteGithubUserViewModel(application: Application) : ViewModel() {
    private val favoriteRepository = FavoriteGithubUserRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteGithubUser>> = favoriteRepository.getAllFavorites()
}