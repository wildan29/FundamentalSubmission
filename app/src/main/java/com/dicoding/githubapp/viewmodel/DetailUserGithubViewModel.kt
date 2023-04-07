package com.dicoding.githubapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.database.FavoriteGithubUser
import com.dicoding.githubapp.model.remote.DetailUser
import com.dicoding.githubapp.repository.DetailUserRepository
import com.dicoding.githubapp.repository.FavoriteGithubUserRepository

class DetailUserGithubViewModel(application: Application) : ViewModel() {

    val repository = DetailUserRepository()

    private val favoriteRepository = FavoriteGithubUserRepository(application)

    fun getToastMsg(): LiveData<String> {
        return repository.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getGithubUser(login: String): LiveData<DetailUser> {
        return repository.getDetailUser(login)
    }


    fun getHtml(login: String): LiveData<DetailUser> {
        return repository.getDetailUserHtml(login)
    }

    fun insert(user: FavoriteGithubUser) {
        favoriteRepository.insert(user)
    }

    fun delete(id: Int) {
        favoriteRepository.delete(id)
    }

    fun getAllFavorites(): LiveData<List<FavoriteGithubUser>> =favoriteRepository.getAllFavorites()

}