package com.dicoding.githubapp.ui.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeSettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        private val THEME_KEY = booleanPreferencesKey("theme_setting")

        @Volatile
        private var INSTANCE: ThemeSettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemeSettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemeSettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}