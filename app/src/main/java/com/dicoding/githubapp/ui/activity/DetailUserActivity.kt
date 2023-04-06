package com.dicoding.githubapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubapp.R
import com.dicoding.githubapp.adapter.SectionPageAdapter
import com.dicoding.githubapp.databinding.ActivityDetailUserBinding
import com.dicoding.githubapp.databinding.BiodataUserPlaceHolderBinding
import com.dicoding.githubapp.model.DetailUser
import com.dicoding.githubapp.util.Utils
import com.dicoding.githubapp.viewmodel.DetailUserGithubViewModel
import com.dicoding.githubapp.viewmodel.FollowersGithubViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var bindingDetail: BiodataUserPlaceHolderBinding
    private lateinit var detailUserViewModel: DetailUserGithubViewModel
    private lateinit var followersViewModel: FollowersGithubViewModel
    private var value = ""

    companion object {
        const val LOGIN_KEY_USER = "login_key_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        bindingDetail = BiodataUserPlaceHolderBinding.bind(binding.root)

        setContentView(binding.root)

        // set view model provider
        detailUserViewModel = ViewModelProvider(this)[DetailUserGithubViewModel::class.java]
        followersViewModel = ViewModelProvider(this)[FollowersGithubViewModel::class.java]

        // get intent
        value = intent.getStringExtra(LOGIN_KEY_USER)!!

        // make back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // change title action bar
        supportActionBar?.title = "Detail User"

        // get detail user
        detailUserViewModel.getGithubUser(value).observe(this) {
            binding.biodataProfile.root.visibility = View.VISIBLE
            setData(it)
        }

        // loading data
        detailUserViewModel.loading().observe(this) {
            Utils.showLoading(binding.progressBar, it)
        }

        // get message
        detailUserViewModel.getToastMsg().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.refresh.setOnRefreshListener {
            refresh()
        }

        setTabLayoutView(value)

    }

    @SuppressLint("SetTextI18n")
    fun setData(detailUser: DetailUser?) {
        bindingDetail.apply {
            Glide.with(this@DetailUserActivity)
                .load(detailUser?.avatarUrl)
                .circleCrop()
                .into(avatar)

            name.text = detailUser?.name ?: "Anonymous"

            username.text = detailUser?.login

            followers.text = "${detailUser?.followers} Followers"

            following.text = "${detailUser?.following} Following"
        }
    }

    fun setTabLayoutView(value: String?) {
        val login = Bundle()
        login.putString(Utils.LOGIN_KEY_USER_FF, value)

        val sectionPagerAdapter = SectionPageAdapter(this@DetailUserActivity, login)

        binding.viewPager.adapter = sectionPagerAdapter

        val tabTitle = resources.getStringArray(R.array.tab)


        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, postion ->
            tab.text = tabTitle[postion]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

            R.id.open_browse -> {
                detailUserViewModel.getHtml(value).observe(this) {
                    startActivity(Intent(ACTION_VIEW, Uri.parse(it.html)))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.open, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun refresh() {

        binding.biodataProfile.root.visibility = View.INVISIBLE

        // get detail user
        detailUserViewModel.getGithubUser(value).observe(this) {
            binding.biodataProfile.root.visibility = View.VISIBLE
            setData(it)
        }

        // loading data
        detailUserViewModel.loading().observe(this) {
            Utils.showLoading(binding.progressBar, it)
        }

        // get message
        detailUserViewModel.getToastMsg().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        setTabLayoutView(value)

        binding.refresh.isRefreshing = false
    }
}