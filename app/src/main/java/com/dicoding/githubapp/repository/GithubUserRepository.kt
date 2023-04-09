package com.dicoding.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubapp.api.ApiConfig
import com.dicoding.githubapp.model.remote.GithubResponeApiItem
import com.dicoding.githubapp.model.remote.GithubResponse
import com.dicoding.githubapp.model.remote.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GithubUserRepository {

    companion object {
        var EXAMPLE = "wildan"
    }

    val toastMsg = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getAllUsers(): LiveData<List<GithubResponeApiItem>> {
        val getUsers = MutableLiveData<List<GithubResponeApiItem>>()
        _isLoading.value = true
        ApiConfig.getApiService().getAllUser().enqueue(object : Callback<List<GithubResponeApiItem>> {
            override fun onResponse(
                call: Call<List<GithubResponeApiItem>>,
                response: Response<List<GithubResponeApiItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    getUsers.value = response.body()
                    Timber.d("${response.body()}")
                } else {
                    Timber.e("onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<GithubResponeApiItem>>, t: Throwable) {
                _isLoading.value = false
                toastMsg.value = "Gagal memuat, periksa koneksi anda!"
            }
        })
        return getUsers
    }

    fun getGithubUsers(): LiveData<List<ItemsItem>> {

        val getUsers = MutableLiveData<List<ItemsItem>>()
        _isLoading.value = true
        ApiConfig.getApiService().getUser(EXAMPLE).enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    getUsers.value = response.body()?.items
                } else {
                    toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                    Timber.e("onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                toastMsg.value = "Gagal memuat, periksa koneksi anda!"
            }
        })

        return getUsers
    }

}