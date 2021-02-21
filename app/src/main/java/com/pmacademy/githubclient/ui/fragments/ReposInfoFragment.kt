package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.RepositoryInfoFragmentBinding
import com.pmacademy.githubclient.ui.base.BaseFragment

class ReposInfoFragment : BaseFragment(R.layout.repository_info_fragment) {

    private lateinit var reposName: String
    private lateinit var userName: String
    private lateinit var binding: RepositoryInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        reposName = requireArguments().getString(KEY_REPOS_NAME).toString()
        userName = requireArguments().getString(KEY_USER_NAME).toString()
        binding = RepositoryInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvReadmeText.text = reposName
        binding.tvReposName.text = userName

    }


    companion object {
        private const val KEY_REPOS_NAME = "KEY_REPOS_NAME"
        private const val KEY_USER_NAME = "KEY_USER_NAME"

        fun newInstance(reposName: String, userName: String): ReposInfoFragment = ReposInfoFragment().apply {
            val bundle = Bundle()
            bundle.putString(KEY_REPOS_NAME, reposName)
            bundle.putString(KEY_USER_NAME, userName)
            this.arguments = bundle
        }
    }
}