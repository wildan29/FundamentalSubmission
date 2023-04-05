package com.dicoding.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubapp.api.ApiConfig
import com.dicoding.githubapp.model.GithubResponse
import com.dicoding.githubapp.model.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SearchUserGithubRepository {
    val toastMsg = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUsersGithub(username: String): LiveData<List<ItemsItem>> {
        val data = MutableLiveData<List<ItemsItem>>()
        _isLoading.value = true
        ApiConfig.getApiService().getUser(username).enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    data.value = response.body()?.items
                } else {
                    toastMsg.value = "Gagal memuat, periksa koneksi anda!!!"
                    Timber.e("onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                toastMsg.value = "Gagal memuat, periksa koneksi anda! yaw"
                _isLoading.value = false
            }
        })

        return data
    }


}