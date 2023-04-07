package com.dicoding.githubapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubapp.databinding.UserListGithubBinding
import com.dicoding.githubapp.model.database.FavoriteGithubUser
import com.dicoding.githubapp.model.remote.GithubUserModel


class FavoriteGithubUserAdapter(private val listFavorteGithubUser: ArrayList<FavoriteGithubUser>) :
    RecyclerView.Adapter<FavoriteGithubUserAdapter.FavoriteGithubUserViewHolder>() {


    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteGithubUser)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FavoriteGithubUserViewHolder(val binding: UserListGithubBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteGithubUserViewHolder {
        val binding =
            UserListGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteGithubUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteGithubUserViewHolder, position: Int) {
        val (_, login, avatar) = listFavorteGithubUser[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context).load(avatar).into(imageUser)
            tvUsername.text = login
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listFavorteGithubUser[holder.adapterPosition])
            }
        }

    }

    override fun getItemCount() = listFavorteGithubUser.size
}