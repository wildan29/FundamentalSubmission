package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.GithubResponeApiItem
import com.dicoding.githubapp.model.ItemsItem
import com.dicoding.githubapp.repository.GithubUserRepository

class GithubUserViewModel : ViewModel() {

    val nilai = GithubUserRepository()

    fun getAllUsersViewModel(): LiveData<List<GithubResponeApiItem>> {
        return nilai.getAllUsers()
    }

    fun getToastMsg(): LiveData<String> {
        return nilai.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return nilai.isLoading
    }

    fun getGithubUser(): LiveData<List<ItemsItem>> {
        return nilai.getGithubUsers()
    }
}