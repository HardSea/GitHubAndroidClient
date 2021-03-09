package com.pmacademy.githubclient.ui.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.api.GithubUtils
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.LoginFragmentBinding
import com.pmacademy.githubclient.ui.base.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class LoginFragment : BaseFragment(R.layout.login_fragment) {

    private lateinit var user: UserResponse
    private lateinit var binding: LoginFragmentBinding

    private val githubUtils: GithubUtils by lazy {
        GithubUtils()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun showErrorMessage(errorRes: Int) {
        binding.btnLogin.visibility = View.GONE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = getString(errorRes)
    }


    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}