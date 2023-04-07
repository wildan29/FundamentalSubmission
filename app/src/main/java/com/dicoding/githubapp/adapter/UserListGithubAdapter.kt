package com.dicoding.githubapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapp.databinding.UserListGithubBinding
import com.dicoding.githubapp.model.remote.GithubUserModel

@SuppressLint("NotifyDataSetChanged")
class UserListGithubAdapter(private val listUserGithub: ArrayList<GithubUserModel>) :
    RecyclerView.Adapter<UserListGithubAdapter.ViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GithubUserModel)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listUserGithub[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount() = listUserGithub.size

}