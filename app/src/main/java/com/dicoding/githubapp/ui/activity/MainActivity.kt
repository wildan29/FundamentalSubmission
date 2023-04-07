package com.dicoding.githubapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.R
import com.dicoding.githubapp.adapter.UserListGithubAdapter
import com.dicoding.githubapp.databinding.ActivityMainBinding
import com.dicoding.githubapp.model.remote.GithubUserModel
import com.dicoding.githubapp.model.remote.ItemsItem
import com.dicoding.githubapp.repository.GithubUserRepository
import com.dicoding.githubapp.ui.factory.SettingViewModelFactory
import com.dicoding.githubapp.ui.preferences.ThemeSettingPreference
import com.dicoding.githubapp.util.Utils
import com.dicoding.githubapp.viewmodel.GithubUserViewModel
import com.dicoding.githubapp.viewmodel.SearchUserGithubViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var getUserViewModel: GithubUserViewModel
    private lateinit var searchUserViewModel: SearchUserGithubViewModel
    private var default = GithubUserRepository.EXAMPLE

    override fun onCreate(savedInstanceState: Bundle?) {
        // splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // change color in night mode or no
        supportActionBar?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.color.action_bar
            )
        )

        val pref = ThemeSettingPreference.getInstance(dataStore)

        // deklarasi viewmodel
        getUserViewModel = ViewModelProvider(this)[GithubUserViewModel::class.java]
        searchUserViewModel = ViewModelProvider(
            this,
            SettingViewModelFactory(pref)
        )[SearchUserGithubViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.color.action_bar
            )!!
        )
        binding.rvUserGithub.addItemDecoration(itemDecoration)

        // get github users
        getUserViewModel.getGithubUser().observe(this) {
            if (it.isEmpty()) {
                binding.notFoundLayout.root.visibility = View.VISIBLE
                binding.rvUserGithub.visibility = View.INVISIBLE
            } else {
                binding.notFoundLayout.root.visibility = View.INVISIBLE
                binding.rvUserGithub.visibility = View.VISIBLE
                getData(it)
            }
        }

        // loading data
        getUserViewModel.loading().observe(this) {
            Utils.showLoading(binding.progressBar, it)
        }

        // toast if data is unsuccessful
        getUserViewModel.getToastMsg().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            binding.rvUserGithub.visibility = View.INVISIBLE
            binding.notFoundLayout.root.visibility = View.VISIBLE
        }

        // refresh
        binding.refresh.setOnRefreshListener {
            refresh()
        }

        //  Call dark mode
        searchUserViewModel.getThemeSetting().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    // set data to adapter
    private fun getData(data: List<ItemsItem>) {
        val list_user = ArrayList<GithubUserModel>()

        for (i in data) {
            list_user.add(GithubUserModel(i.login, i.avatarUrl))
        }

        val adapter = UserListGithubAdapter(list_user)
        binding.rvUserGithub.adapter = adapter

        adapter.setOnItemClickCallback(object : UserListGithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUserModel) {
                startActivity(
                    Intent(this@MainActivity, DetailUserActivity::class.java).putExtra(
                        DetailUserActivity.LOGIN_KEY_USER,
                        data.login
                    )
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.search -> {
                val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

                val searchView = item.actionView as SearchView

                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

                searchView.queryHint = "Masukkan nama"

                // aksi search view
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    // ketika ditekan
                    override fun onQueryTextSubmit(query: String): Boolean {
                        default = query
                        binding.rvUserGithub.visibility = View.INVISIBLE

                        searchUserViewModel.getGithubUser(query).observe(this@MainActivity) {
                            if (it.isEmpty()) {
                                binding.notFoundLayout.root.visibility = View.VISIBLE
                                binding.rvUserGithub.visibility = View.INVISIBLE
                            } else {
                                binding.notFoundLayout.root.visibility = View.INVISIBLE
                                binding.rvUserGithub.visibility = View.VISIBLE
                                getData(it)
                            }
                        }

                        // loading data
                        searchUserViewModel.loading().observe(this@MainActivity) {
                            Utils.showLoading(binding.progressBar, it)
                        }

                        // toast if data is unsuccessful
                        searchUserViewModel.getToastMsg().observe(this@MainActivity) {
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                            binding.notFoundLayout.root.visibility = View.VISIBLE
                            binding.rvUserGithub.visibility = View.INVISIBLE
                        }
                        return false
                    }

                    // ketika teks berubah
                    override fun onQueryTextChange(newText: String): Boolean {
                        if (newText.isNotEmpty()) {
                            default = newText
                            binding.rvUserGithub.visibility = View.INVISIBLE
                            searchUserViewModel.getGithubUser(newText).observe(this@MainActivity) {
                                if (it.isEmpty()) {
                                    binding.notFoundLayout.root.visibility = View.VISIBLE
                                    binding.rvUserGithub.visibility = View.INVISIBLE
                                } else {
                                    binding.notFoundLayout.root.visibility = View.INVISIBLE
                                    binding.rvUserGithub.visibility = View.VISIBLE
                                    getData(it)
                                }
                            }

                            // loading data
                            searchUserViewModel.loading().observe(this@MainActivity) {
                                Utils.showLoading(binding.progressBar, it)
                            }

                            // toast if data is unsuccessful
                            searchUserViewModel.getToastMsg().observe(this@MainActivity) {
                                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                                binding.rvUserGithub.visibility = View.INVISIBLE
                                binding.notFoundLayout.root.visibility = View.VISIBLE
                            }
                        }
                        return false
                    }
                })

                // ketika back button ditekan akan set kembali data ke awal
                item.setOnActionExpandListener(
                    object : MenuItem.OnActionExpandListener {
                        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                            return true
                        }

                        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {

                            default = "wildan"

                            GithubUserRepository.EXAMPLE = default

                            binding.rvUserGithub.visibility = View.INVISIBLE

                            // get github users
                            getUserViewModel.getGithubUser().observe(this@MainActivity) {
                                if (it.isEmpty()) {
                                    binding.notFoundLayout.root.visibility = View.VISIBLE
                                    binding.rvUserGithub.visibility = View.INVISIBLE
                                } else {
                                    binding.notFoundLayout.root.visibility = View.INVISIBLE
                                    binding.rvUserGithub.visibility = View.VISIBLE
                                    getData(it)
                                }
                            }

                            // loading data
                            getUserViewModel.loading().observe(this@MainActivity) {
                                Utils.showLoading(binding.progressBar, it)
                            }

                            // toast if data is unsuccessful
                            getUserViewModel.getToastMsg().observe(this@MainActivity) {
                                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                                binding.rvUserGithub.visibility = View.INVISIBLE
                                binding.notFoundLayout.root.visibility = View.VISIBLE
                            }
                            return true
                        }
                    })

            }

            R.id.theme -> {
                startActivity(Intent(this, ThemeSettingActivity::class.java))
            }

            R.id.favorites -> {
                startActivity(Intent(this, FavoriteGithubUserActivity::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

    // method refresh
    private fun refresh() {
        GithubUserRepository.EXAMPLE = default

        binding.rvUserGithub.visibility = View.INVISIBLE

        // get github users
        getUserViewModel.getGithubUser().observe(this) {
            if (it.isEmpty()) {
                binding.notFoundLayout.root.visibility = View.VISIBLE
                binding.rvUserGithub.visibility = View.INVISIBLE
            } else {
                binding.notFoundLayout.root.visibility = View.INVISIBLE
                binding.rvUserGithub.visibility = View.VISIBLE
                getData(it)
            }
        }

        // loading data
        getUserViewModel.loading().observe(this) {
            Utils.showLoading(binding.progressBar, it)
        }

        // toast if data is unsuccessful
        getUserViewModel.getToastMsg().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            binding.rvUserGithub.visibility = View.INVISIBLE
            binding.notFoundLayout.root.visibility = View.VISIBLE
        }

        binding.refresh.isRefreshing = false
    }

}