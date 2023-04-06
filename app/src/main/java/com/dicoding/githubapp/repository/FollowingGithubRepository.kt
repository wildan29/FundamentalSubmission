package com.dicoding.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubapp.api.ApiConfig
import com.dicoding.githubapp.model.FollowingGithubModelItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FollowingGithubRepository {

    val toastMsg = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowingGithub(login: String): LiveData<List<FollowingGithubModelItem>> {
        val getFollowing = MutableLiveData<List<FollowingGithubModelItem>>()
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowing(login)
            .enqueue(object : Callback<List<FollowingGithubModelItem>> {
                override fun onResponse(
                    call: Call<List<FollowingGithubModelItem>>,
                    response: Response<List<FollowingGithubModelItem>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        getFollowing.value = response.body()
                    } else {
                        toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                        Timber.e("onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<FollowingGithubModelItem>>, t: Throwable) {
                    _isLoading.value = false
                    toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                }
            })

        return getFollowing
    }

}