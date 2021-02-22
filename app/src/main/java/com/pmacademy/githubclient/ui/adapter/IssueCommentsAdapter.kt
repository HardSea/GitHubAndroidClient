package com.pmacademy.githubclient.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.IssueCommentResponse

private class IssueCommentsDiffCallback : DiffUtil.ItemCallback<IssueCommentResponse>() {
    override fun areItemsTheSame(oldItem: IssueCommentResponse, newItem: IssueCommentResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: IssueCommentResponse, newItem: IssueCommentResponse): Boolean {
        return oldItem.id == newItem.id
    }
}

class IssueCommentsAdapter(private val issueClickListener: (IssueCommentResponse) -> Unit) :
    ListAdapter<IssueCommentResponse, RecyclerView.ViewHolder>(IssueCommentsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_issue_comment, parent, false)
        return IssueCommentsListViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG111", "${getItem(position)}")
        when (holder) {
            is IssueCommentsListViewHolder -> holder.bind(getItem(position), issueClickListener)
        }
    }

    fun updateIssuesList(list: List<IssueCommentResponse>) {
        this.submitList(list)
    }

    class IssueCommentsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var tvIssueCommentBody: TextView? = null

        init {
            tvIssueCommentBody = view.findViewById(R.id.tv_issue_comment_body)
        }

        fun bind(issueCommentItem: IssueCommentResponse, onReposClick: (IssueCommentResponse) -> Unit) {

            tvIssueCommentBody?.text = issueCommentItem.body
            itemView.setOnClickListener {
                onReposClick.invoke(issueCommentItem)
            }
        }
    }

}

