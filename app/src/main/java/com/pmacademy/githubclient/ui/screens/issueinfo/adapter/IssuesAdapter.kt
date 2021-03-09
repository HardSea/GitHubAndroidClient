package com.pmacademy.githubclient.ui.screens.issueinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.IssueResponse

private class IssueItemDiffCallback : DiffUtil.ItemCallback<IssueResponse>() {
    override fun areItemsTheSame(oldItem: IssueResponse, newItem: IssueResponse): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: IssueResponse, newItem: IssueResponse): Boolean {
        return oldItem == newItem
    }
}

class IssueAdapter(private val issueClickListener: (IssueResponse) -> Unit) :
    ListAdapter<IssueResponse, RecyclerView.ViewHolder>(IssueItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_issues_item, parent, false)
        return IssuesListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IssuesListViewHolder -> holder.bind(getItem(position), issueClickListener)
        }
    }

    fun updateIssuesList(list: List<IssueResponse>) {
        this.submitList(list)
    }

    class IssuesListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvIssueTitle: TextView? = null

        init {
            tvIssueTitle = view.findViewById(R.id.tv_issue_title)
        }

        fun bind(issueItem: IssueResponse, onReposClick: (IssueResponse) -> Unit) {
            tvIssueTitle?.text = issueItem.title
            itemView.setOnClickListener {
                onReposClick.invoke(issueItem)
            }
        }
    }
}

