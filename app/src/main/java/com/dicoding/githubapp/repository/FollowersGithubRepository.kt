package com.dicoding.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubapp.api.ApiConfig
import com.dicoding.githubapp.model.FollowersGithubModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FollowersGithubRepository {

    val toastMsg = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowersGithub(login: String): LiveData<List<FollowersGithubModelItem>> {
        val getFollowers = MutableLiveData<List<FollowersGithubModelItem>>()
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowers(login)
            .enqueue(object : Callback<List<FollowersGithubModelItem>> {
                override fun onResponse(
                    call: Call<List<FollowersGithubModelItem>>,
                    response: Response<List<FollowersGithubModelItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        getFollowers.value = response.body()
                    } else {
                        toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                        Timber.e("onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowersGithubModelItem>>, t: Throwable) {
                    _isLoading.value = false
                    toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                }
            })

        return getFollowers
    }
}