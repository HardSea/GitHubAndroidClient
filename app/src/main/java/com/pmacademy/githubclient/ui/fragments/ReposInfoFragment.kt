package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.ui.base.BaseFragment

class ReposInfoFragment: BaseFragment(R.layout.project_info_fragment) {

    companion object {
        fun newInstance(): ReposInfoFragment {
            return ReposInfoFragment()
        }
    }
}