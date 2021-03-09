package com.pmacademy.githubclient.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.ui.screens.issueinfo.IssueInfoFragment
import com.pmacademy.githubclient.ui.screens.login.LoginFragment
import com.pmacademy.githubclient.ui.screens.repoinfo.RepoInfoFragment
import com.pmacademy.githubclient.ui.screens.search.UsersSearchFragment
import com.pmacademy.githubclient.ui.screens.userinfo.UserInfoFragment
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FragmentNavigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int,
) {

    fun showIssueInfoFragment(
        issue: IssueResponse,
        userName: String,
        repoName: String
    ) {
        fragmentManager.beginTransaction()
            .replace(
                container,
                IssueInfoFragment.newInstance(
                    issue = issue,
                    userName = userName,
                    repoName = repoName
                )
            )
            .addToBackStack(null)
            .commit()
    }

    fun showLoginFragment() {
        fragmentManager.beginTransaction()
            .replace(container, LoginFragment.newInstance())
            .commit()
    }

    fun showProjectInfoFragment(reposName: String, userName: String) {
        fragmentManager.beginTransaction()
            .replace(container, RepoInfoFragment.newInstance(reposName, userName))
            .addToBackStack(null)
            .commit()
    }

    fun showUserInfoFragment(user: UserResponse, addToBackStack: Boolean = true) {
        val transaction = fragmentManager.beginTransaction()
            .replace(container, UserInfoFragment.newInstance(user))
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }

    fun showUsersSearchFragment() {
        fragmentManager.beginTransaction()
            .replace(container, UsersSearchFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

}