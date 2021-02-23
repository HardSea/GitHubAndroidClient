package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.ui.base.BaseFragment

class ReposIssuesFragment : BaseFragment(R.layout.project_issues_fragment){

    companion object {
        fun newInstance(): ReposIssuesFragment {
            return ReposIssuesFragment()
        }
    }
}