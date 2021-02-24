package com.pmacademy.githubclient.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.myapplicationtemp.data.ReposResponse


private class SearchItemDiffCallback : DiffUtil.ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.login == newItem.login
    }
}

class UsersSearchAdapter(private val searchClickListener: (String) -> Unit) :
    ListAdapter<UserResponse, RecyclerView.ViewHolder>(SearchItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.test_item, parent, false)
        Log.d("SearchLog", "UsersSearchAdapter -> onCreateViewHolder()")
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserListViewHolder -> holder.bind(getItem(position), searchClickListener)
           /* Log.d("StarWars", "UsersSearchAdapter -> onBindViewHolder()")*/
        }
    }

    fun updateUsersList(list: List<UserResponse>) {
        Log.d("SearchLog", "UsersSearchAdapter -> updateUsersList()")
        this.submitList(list)
    }

    class UserListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvUserLoginSearch: TextView? = null
        private var ivLogo: ImageView? = null
        init {
            Log.d("SearchLog", "UserListViewHolder -> init")
            tvUserLoginSearch = view.findViewById(R.id.tvUserLoginSearch)
            ivLogo = view.findViewById(R.id.ivLogo)
        }

        fun bind(user: UserResponse, onReposClick: (String) -> Unit) {
            tvUserLoginSearch?.text = user.login
            itemView.setOnClickListener {
                Log.d("SearchLog", "UserListViewHolder -> bind() -> setOnClickListener()")
                onReposClick.invoke(user.login)
            }
        }
    }
}