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
import com.pmacademy.githubclient.data.model.ReactionType

private class IssueCommentsDiffCallback : DiffUtil.ItemCallback<IssueCommentResponse>() {
    override fun areItemsTheSame(
        oldItem: IssueCommentResponse,
        newItem: IssueCommentResponse
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: IssueCommentResponse,
        newItem: IssueCommentResponse
    ): Boolean {
        return oldItem.id == newItem.id
    }
}

class IssueCommentsAdapter(private val issueClickListener: (IssueCommentResponse, ReactionType) -> Unit) :
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
        private var tvLike: TextView? = null
        private var tvDislike: TextView? = null
        private var tvLaugh: TextView? = null
        private var tvConfused: TextView? = null
        private var tvHeart: TextView? = null
        private var tvHooray: TextView? = null
        private var tvRocket: TextView? = null
        private var tvEyes: TextView? = null


        init {
            tvIssueCommentBody = view.findViewById(R.id.tv_issue_comment_body)
            tvLike = view.findViewById(R.id.tv_like)
            tvDislike = view.findViewById(R.id.tv_dislike)
            tvLaugh = view.findViewById(R.id.tv_laugh)
            tvConfused = view.findViewById(R.id.tv_confused)
            tvHeart = view.findViewById(R.id.tv_heart)
            tvHooray = view.findViewById(R.id.tv_hooray)
            tvRocket = view.findViewById(R.id.tv_rocket)
            tvEyes = view.findViewById(R.id.tv_eyes)
        }

        fun bind(
            issueCommentItem: IssueCommentResponse,
            onReposClick: (IssueCommentResponse, ReactionType) -> Unit
        ) {

            tvIssueCommentBody?.text = issueCommentItem.body
            tvLike?.setOnClickListener { onReposClick.invoke(issueCommentItem, ReactionType.LIKE) }
            tvDislike?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.DISLIKE
                )
            }
            tvLaugh?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.LAUGH
                )
            }
            tvConfused?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.CONFUSED
                )
            }
            tvHeart?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.HEART
                )
            }
            tvHooray?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.HOORAY
                )
            }
            tvRocket?.setOnClickListener {
                onReposClick.invoke(
                    issueCommentItem,
                    ReactionType.ROCKET
                )
            }
            tvEyes?.setOnClickListener { onReposClick.invoke(issueCommentItem, ReactionType.EYES) }


        }
    }

}

