package com.pmacademy.githubclient.ui.base

import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.MainActivity
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.ui.FragmentNavigator
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
    protected val sharedPreferences by lazy { SharedPref(requireContext()) }
}