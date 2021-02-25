package com.pmacademy.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse


private class SearchItemDiffCallback : DiffUtil.ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.login == newItem.login
    }
}

class UsersSearchAdapter(private val searchClickListener: (UserResponse) -> Unit) :
    ListAdapter<UserResponse, RecyclerView.ViewHolder>(SearchItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_user_search_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserListViewHolder -> holder.bind(getItem(position), searchClickListener)
        }
    }

    fun updateUsersList(list: List<UserResponse>) {
        this.submitList(list)
    }

    class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvUserLoginSearch: TextView? = null
        private var ivAvatar: ImageView? = null

        init {
            tvUserLoginSearch = view.findViewById(R.id.tv_user_login_search)
            ivAvatar = view.findViewById(R.id.iv_user_avatar)
        }

        fun bind(user: UserResponse, onUserClick: (UserResponse) -> Unit) {
            tvUserLoginSearch?.text = user.login

            ivAvatar?.let {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(it)
            }
            itemView.setOnClickListener {
                onUserClick.invoke(user)
            }
        }
    }
}