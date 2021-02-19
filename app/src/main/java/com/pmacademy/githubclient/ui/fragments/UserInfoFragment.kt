package com.pmacademy.githubclient.ui.fragments

import com.pmacademy.githubclient.R


class UserInfoFragment: BaseFragment(R.layout.user_info_fragment) {

    companion object {
        fun newInstance(): UserInfoFragment {
            return UserInfoFragment()
        }
    }
}