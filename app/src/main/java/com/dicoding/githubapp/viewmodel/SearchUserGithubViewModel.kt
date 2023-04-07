package com.dicoding.githubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubapp.model.remote.ItemsItem
import com.dicoding.githubapp.repository.SearchUserGithubRepository
import com.dicoding.githubapp.ui.preferences.ThemeSettingPreference

class SearchUserGithubViewModel(private val pref: ThemeSettingPreference) : ViewModel() {
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

    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}