package com.dicoding.githubapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.dicoding.githubapp.ui.preferences.ThemeSettingPreference
import com.dicoding.githubapp.viewmodel.SearchUserGithubViewModel
import com.dicoding.githubapp.viewmodel.ThemeSettingsViewModel

class SettingViewModelFactory(private val pref: ThemeSettingPreference) : NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
            return ThemeSettingsViewModel(pref) as T
        }else if(modelClass.isAssignableFrom(SearchUserGithubViewModel::class.java)){
            return SearchUserGithubViewModel(pref) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
    }
}