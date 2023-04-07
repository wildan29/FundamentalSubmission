package com.dicoding.githubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubapp.model.database.FavoriteGithubUser
import com.dicoding.githubapp.model.database.FavoriteGithubUserDao
import com.dicoding.githubapp.model.database.FavoriteUserGithubDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteGithubUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteGithubUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserGithubDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavorites(): LiveData<List<FavoriteGithubUser>> = mFavoriteUserDao.getAllUser()

    fun insert(user: FavoriteGithubUser) {
        executorService.execute { mFavoriteUserDao.insertFavorite(user) }
    }

    fun delete(id: Int) {
        executorService.execute { mFavoriteUserDao.removeFavorite(id) }
    }
}