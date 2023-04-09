package com.dicoding.githubapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubapp.R
import com.dicoding.githubapp.adapter.FavoriteGithubUserAdapter
import com.dicoding.githubapp.databinding.ActivityFavoriteGithubUserBinding
import com.dicoding.githubapp.model.database.FavoriteGithubUser
import com.dicoding.githubapp.ui.factory.FavoriteGithubUserViewModelFactory
import com.dicoding.githubapp.viewmodel.FavoriteGithubUserViewModel

class FavoriteGithubUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteGithubUserBinding
    private lateinit var favoriteGithubUserViewModel: FavoriteGithubUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteGithubUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // change action bar title and back button
        supportActionBar?.apply {
            title = resources.getString(R.string.favorite)
            setDisplayHomeAsUpEnabled(true)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteGithubUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.color.action_bar
            )!!
        )
        binding.rvFavoriteGithubUser.addItemDecoration(itemDecoration)

        // set view model
        favoriteGithubUserViewModel =
            ViewModelProvider(
                this,
                FavoriteGithubUserViewModelFactory.getInstance(this.application)
            )[FavoriteGithubUserViewModel::class.java]

        favoriteGithubUserViewModel.getAllFavorites().observe(this) {
            if (it != null) {
                getData(it)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData(data: List<FavoriteGithubUser>) {
        val adapter = FavoriteGithubUserAdapter(data as ArrayList<FavoriteGithubUser>)
        binding.rvFavoriteGithubUser.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteGithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteGithubUser) {
                startActivity(
                    Intent(
                        this@FavoriteGithubUserActivity,
                        DetailUserActivity::class.java
                    ).putExtra(
                        DetailUserActivity.LOGIN_KEY_USER,
                        data.login
                    )
                )
            }
        })
    }
}