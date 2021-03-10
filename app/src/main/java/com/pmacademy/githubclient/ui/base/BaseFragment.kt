package com.pmacademy.githubclient.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.FragmentNavigator
import com.pmacademy.githubclient.ui.MainActivity
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }

    //protected val sharedPreferences by lazy { SharedPref(requireContext()) }

    @Inject
    lateinit var sharedPreferences: SharedPref

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDi()
    }

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

    protected abstract fun setupDi()
    protected abstract fun showErrorMessage(@StringRes errorRes: Int)

}