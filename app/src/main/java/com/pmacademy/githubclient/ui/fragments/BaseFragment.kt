package com.pmacademy.githubclient.ui.fragments

import androidx.fragment.app.Fragment
import com.pmacademy.githubclient.MainActivity

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    protected val navigator: FragmentNavigator by lazy {
        (requireActivity() as MainActivity).fragmentNavigator
    }
}