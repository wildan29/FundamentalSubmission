package com.dicoding.githubapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteGithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(user: FavoriteGithubUser)

    @Query("DELETE FROM FavoriteGithubUser WHERE FavoriteGithubUser.id = :id")
    fun removeFavorite(id: Int)

    @Query("SELECT * FROM FavoriteGithubUser ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavoriteGithubUser>>

    @Query("SELECT * FROM FavoriteGithubUser WHERE FavoriteGithubUser.id = :id")
    fun getUserById(id: Int): LiveData<FavoriteGithubUser>
}