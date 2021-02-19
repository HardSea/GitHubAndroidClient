package com.pmacademy.githubclient.ui.fragments

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager

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
            .replace(container, ProjectInfoFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showProjectIssuesFragment() {
        fragmentManager.beginTransaction()
            .replace(container, ProjectIssuesFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun showUserInfoFragment() {
        fragmentManager.beginTransaction()
            .replace(container, UserInfoFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}