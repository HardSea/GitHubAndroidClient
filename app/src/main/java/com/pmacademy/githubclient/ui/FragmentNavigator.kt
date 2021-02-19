package com.pmacademy.githubclient.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.ui.fragments.*
import com.pmacademy.githubclient.data.model.UserResponse
import kotlinx.serialization.ExperimentalSerializationApi

class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int,
) {

    fun showIssueCommentsFragment() {
        fragmentManager.beginTransaction()
            .replace(container, IssueCommentsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showLoginFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoginFragment.newInstance())
            .commit()
    }

    fun showProjectInfoFragment() {
        fragmentManager.beginTransaction()
            .replace(container, ReposInfoFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showProjectIssuesFragment() {
        fragmentManager.beginTransaction()
            .replace(container, ReposIssuesFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    @ExperimentalSerializationApi
    fun showUserInfoFragment(user: UserResponse) {
        fragmentManager.beginTransaction()
            .replace(container, UserInfoFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }
}