package com.pmacademy.githubclient.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.ui.base.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class LoginFragment : BaseFragment(R.layout.login_fragment) {

    private lateinit var user: UserResponse

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            startGitHubLogin()
        }
    }

    private fun startGitHubLogin() {
        val authIntent = Intent(Intent.ACTION_VIEW, githubUtils.buildAuthGitHubUrl())
        startActivity(authIntent)
    }

    private fun saveUserToken(code: String) {
        GlobalScope.launch {
            val response = githubUtils.getAccessToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.token = token

            user = githubUtils.getUser(token).successResult

            saveUserToSharedPreference()
            navigator.showUserInfoFragment(user, addToBackStack = false)
            requireActivity().intent.data = null
        }
    }

    private fun saveUserToSharedPreference() {
        sharedPreferences.localUserName = user.login
        sharedPreferences.localUserAvatarUrl = user.avatarUrl
    }

    override fun onResume() {
        super.onResume()
        val code = githubUtils.getCodeFromUri(uri = requireActivity().intent.data)
        code ?: return
        saveUserToken(code)
    }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName

        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}