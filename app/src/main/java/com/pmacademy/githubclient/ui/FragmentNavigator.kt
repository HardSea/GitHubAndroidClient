package com.pmacademy.githubclient.ui

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.ui.fragments.IssueInfoFragment
import com.pmacademy.githubclient.ui.fragments.LoginFragment
import com.pmacademy.githubclient.ui.fragments.ReposInfoFragment
import com.pmacademy.githubclient.ui.fragments.UserInfoFragment
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
            .replace(container, ReposInfoFragment.newInstance(reposName, userName))
            .addToBackStack(null)
            .commit()
    }

    fun showUserInfoFragment(user: UserResponse, addToBackStack: Boolean = true) {
        Log.d("TAG43", "showUserInfoFragment: addToBackStack")
        val transaction = fragmentManager.beginTransaction()
            .replace(container, UserInfoFragment.newInstance(user))
        if (addToBackStack) transaction.addToBackStack(null)
        transaction.commit()
    }
}