package com.dicoding.githubapp.ui.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapp.viewmodel.DetailUserGithubViewModel
import com.dicoding.githubapp.viewmodel.FavoriteGithubUserViewModel

class FavoriteGithubUserViewModelFactory  constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteGithubUserViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavoriteGithubUserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavoriteGithubUserViewModelFactory::class.java) {
                    INSTANCE = FavoriteGithubUserViewModelFactory(application)
                }
            }
            return INSTANCE as FavoriteGithubUserViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserGithubViewModel::class.java)) {
            return DetailUserGithubViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteGithubUserViewModel::class.java)) {
            return FavoriteGithubUserViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: ${modelClass.name}")
    }
}