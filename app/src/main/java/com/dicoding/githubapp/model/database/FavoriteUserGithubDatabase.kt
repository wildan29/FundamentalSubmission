package com.dicoding.githubapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteGithubUser::class], version = 5)
abstract class FavoriteUserGithubDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteGithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteUserGithubDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserGithubDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteUserGithubDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUserGithubDatabase::class.java, "favorite_user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteUserGithubDatabase
        }
    }
}