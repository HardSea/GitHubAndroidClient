package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.ui.base.BaseFragment

class IssueCommentsFragment: BaseFragment(R.layout.comments_issue_fragment) {


    companion object {
        fun newInstance(): IssueCommentsFragment {
            return IssueCommentsFragment()
        }
    }
}