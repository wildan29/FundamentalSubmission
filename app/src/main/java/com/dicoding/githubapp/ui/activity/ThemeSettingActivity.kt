package com.dicoding.githubapp.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubapp.R
import com.dicoding.githubapp.databinding.ActivityThemeSettingBinding
import com.dicoding.githubapp.ui.factory.SettingViewModelFactory
import com.dicoding.githubapp.ui.preferences.ThemeSettingPreference
import com.dicoding.githubapp.viewmodel.ThemeSettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeSettingBinding
    private lateinit var themeSettingViewModel: ThemeSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // change action bar
        supportActionBar?.title = resources.getString(R.string.setting)

        val preference = ThemeSettingPreference.getInstance(dataStore)

        themeSettingViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(preference)
        )[ThemeSettingsViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        themeSettingViewModel.getThemeSettings().observe(this) {
            binding.apply {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeSettingViewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}