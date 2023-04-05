package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.ItemsItem
import com.dicoding.githubapp.repository.SearchUserGithubRepository

class SearchUserGithubViewModel : ViewModel() {
    private val repository = SearchUserGithubRepository()

    fun getToastMsg(): LiveData<String> {
        return repository.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getGithubUser(username: String): LiveData<List<ItemsItem>> {
        return repository.searchUsersGithub(username)
    }
}