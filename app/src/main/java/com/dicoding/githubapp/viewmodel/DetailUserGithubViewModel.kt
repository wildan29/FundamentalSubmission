package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.DetailUser
import com.dicoding.githubapp.repository.DetailUserRepository

class DetailUserGithubViewModel : ViewModel() {

    val repository = DetailUserRepository()

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


}