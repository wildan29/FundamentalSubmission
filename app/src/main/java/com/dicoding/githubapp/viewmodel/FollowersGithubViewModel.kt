package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.FollowersGithubModelItem
import com.dicoding.githubapp.repository.FollowersGithubRepository

class FollowersGithubViewModel : ViewModel() {

    private val repository = FollowersGithubRepository()

    fun getToastMsg(): LiveData<String> {
        return repository.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getFollowers(login: String): LiveData<List<FollowersGithubModelItem>> {
        return repository.getFollowersGithub(login)
    }

}