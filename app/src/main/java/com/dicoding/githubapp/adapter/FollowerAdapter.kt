package com.dicoding.githubapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapp.databinding.UserListGithubBinding
import com.dicoding.githubapp.model.FollowersGithubModelItem

class FollowerAdapter(private val listUserGithub: ArrayList<FollowersGithubModelItem>) :
    RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    class ViewHolder(val binding: UserListGithubBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            UserListGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (login, imgUrl) = listUserGithub[position]

        with(holder) {
            Glide.with(itemView.context).load(imgUrl).into(binding.imageUser)
            binding.tvUsername.text = login
        }
    }

    override fun getItemCount() = listUserGithub.size

}