package com.pmacademy.githubclient.ui.base

import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.MainActivity
import com.pmacademy.githubclient.ui.FragmentNavigator

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
}