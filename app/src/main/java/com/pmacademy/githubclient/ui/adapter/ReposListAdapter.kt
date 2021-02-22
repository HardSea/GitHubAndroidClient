package com.pmacademy.githubclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.R
import com.pmacademy.myapplicationtemp.data.ReposResponse

private class ReposItemDiffCallback : DiffUtil.ItemCallback<ReposResponse>() {
    override fun areItemsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ReposResponse, newItem: ReposResponse): Boolean {
        return oldItem.id == newItem.id
    }
}

class ReposListAdapter(private val reposClickListener: (String) -> Unit) :
    ListAdapter<ReposResponse, RecyclerView.ViewHolder>(ReposItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_repos_item, parent, false)
        return ReposListViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReposListViewHolder -> holder.bind(getItem(position), reposClickListener)
        }
    }

    fun updateReposList(list: List<ReposResponse>) {
        this.submitList(list)
    }

    class ReposListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvReposName: TextView? = null

        init {
            tvReposName = view.findViewById(R.id.tv_repos_name)
        }

        fun bind(reposItem: ReposResponse, onReposClick: (String) -> Unit) {
            tvReposName?.text = reposItem.name
            itemView.setOnClickListener {
                onReposClick.invoke(reposItem.name)
            }
        }
    }

}



