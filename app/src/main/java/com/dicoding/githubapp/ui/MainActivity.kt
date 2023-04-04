package com.dicoding.githubapp.ui

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.R
import com.dicoding.githubapp.adapter.UserListGithub
import com.dicoding.githubapp.databinding.ActivityMainBinding
import com.dicoding.githubapp.model.GithubResponeApiItem
import com.dicoding.githubapp.model.GithubUserModel
import com.dicoding.githubapp.model.ItemsItem
import com.dicoding.githubapp.repository.GithubUserRepository
import com.dicoding.githubapp.viewmodel.GithubUserViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GithubUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        // splash screen
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // change status bar color icon
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


        viewModel = ViewModelProvider(this)[GithubUserViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserGithub.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserGithub.addItemDecoration(itemDecoration)

        viewModel.getGithubUser().observe(this) {
            getData(it)
        }

        viewModel.loading().observe(this) {
            showLoading(it)
        }

        viewModel.getToastMsg().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun getData(data: List<ItemsItem>) {
        val list_user = ArrayList<GithubUserModel>()

        for (i in data) {
            list_user.add(GithubUserModel(i.login, i.avatarUrl))
        }

        val adapter = UserListGithub(list_user)
        binding.rvUserGithub.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = item.actionView as SearchView

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

            searchView.queryHint = "Masukkan nama"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })

        }
        return super.onOptionsItemSelected(item)
    }
}