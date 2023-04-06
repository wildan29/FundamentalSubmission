package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubapp.model.FollowingGithubModelItem
import com.dicoding.githubapp.repository.FollowingGithubRepository

class FollowingGithubViewModel : ViewModel() {

    val repository = FollowingGithubRepository()

    fun getToastMsg(): LiveData<String> {
        return repository.toastMsg
    }

    fun loading(): LiveData<Boolean> {
        return repository.isLoading
    }

    fun getFollowing(login: String): LiveData<List<FollowingGithubModelItem>> {
        return repository.getFollowingGithub(login)
    }

}