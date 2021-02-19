package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R

class ProjectIssuesFragment :BaseFragment(R.layout.project_issues_fragment){

    companion object {
        fun newInstance(): ProjectIssuesFragment {
            return ProjectIssuesFragment()
        }
    }
}