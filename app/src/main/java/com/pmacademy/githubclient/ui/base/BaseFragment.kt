package com.pmacademy.githubclient.ui.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.MainActivity
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.FragmentNavigator
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
    protected val sharedPreferences by lazy { SharedPref(requireContext()) }

    protected fun handleError(error: GithubError) {
        when (error) {
            GithubError.UNAUTHORIZED -> {
                Toast.makeText(
                    requireContext(),
                    getString(error.textErrorRes),
                    Toast.LENGTH_SHORT
                ).show()
                navigator.showLoginFragment()
            }
            else -> showErrorMessage(error.textErrorRes)
        }
    }

    protected abstract fun showErrorMessage(@StringRes errorRes: Int)
}