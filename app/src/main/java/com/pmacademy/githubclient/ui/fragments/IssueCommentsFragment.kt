package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R

class IssueCommentsFragment: BaseFragment(R.layout.comments_issue_fragment) {


    companion object {
        fun newInstance(): IssueCommentsFragment {
            return IssueCommentsFragment()
        }
    }
}