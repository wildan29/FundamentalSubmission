package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.GithubResponeApiItem
import com.dicoding.githubapp.model.ItemsItem
import com.dicoding.githubapp.repository.GithubUserRepository

class GithubUserViewModel : ViewModel() {

    private val repository = GithubUserRepository()

    fun getAllUsersViewModel(): LiveData<List<GithubResponeApiItem>> {
        return repository.getAllUsers()
    }

    fun getToastMsg(): LiveData<String> {
        return repository.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getGithubUser(): LiveData<List<ItemsItem>> {
        return repository.getGithubUsers()
    }

}