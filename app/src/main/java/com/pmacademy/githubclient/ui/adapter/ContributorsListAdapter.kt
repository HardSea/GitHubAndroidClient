package com.pmacademy.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse


private class ContributorItemDiffCallback : DiffUtil.ItemCallback<UserResponse>() {
    override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
        return oldItem.login == newItem.login
    }
}

class ContributorsListAdapter(private val contributorClickListener: (UserResponse) -> Unit) :
    ListAdapter<UserResponse, RecyclerView.ViewHolder>(ContributorItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_contributors_item, parent, false)
        return ContributorsListViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ContributorsListViewHolder -> holder.bind(
                getItem(position),
                contributorClickListener
            )
        }
    }

    fun updateContributorsList(list: List<UserResponse>) {
        this.submitList(list)
    }

    class ContributorsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvContributorName: TextView? = null

        init {
            tvContributorName = view.findViewById(R.id.tv_contributor_name)
        }

        fun bind(contributorUser: UserResponse, onContributorClick: (UserResponse) -> Unit) {
            tvContributorName?.text = contributorUser.login
            itemView.setOnClickListener {
                onContributorClick.invoke(contributorUser)
            }
        }
    }

}

