package com.dicoding.githubapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubapp.api.ApiConfig
import com.dicoding.githubapp.model.DetailUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DetailUserRepository {

    val toastMsg = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailUser(login: String): LiveData<DetailUser> {
        val getDetailUser = MutableLiveData<DetailUser>()
        _isLoading.value = true

        ApiConfig.getApiService().getUserDetail(login).enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    getDetailUser.value = response.body()
                } else {
                    toastMsg.value = "Gagal memuat, periksa koneksi anda!"
                    Timber.e("onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                _isLoading.value = false
                toastMsg.value = "Gagal memuat, periksa koneksi anda!"
            }
        })

        return getDetailUser
    }
}