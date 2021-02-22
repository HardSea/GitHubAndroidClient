package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.RepositoryInfoFragmentBinding
import com.pmacademy.githubclient.ui.adapter.ContributorsListAdapter
import com.pmacademy.githubclient.ui.adapter.IssueListAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.ReposInfoViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReposInfoFragment : BaseFragment(R.layout.repository_info_fragment) {

    private lateinit var repoName: String
    private lateinit var userName: String
    private lateinit var binding: RepositoryInfoFragmentBinding
    private val viewModel: ReposInfoViewModel by viewModels()
    private val contributorsListAdapter = ContributorsListAdapter()
    private val issuesListAdapter = IssueListAdapter { issueResponse ->
        navigator.showIssueInfoFragment(
            issue = issueResponse,
            userName = userName,
            repoName = repoName
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repoName = requireArguments().getString(KEY_REPOS_NAME).toString()
        userName = requireArguments().getString(KEY_USER_NAME).toString()
        binding = RepositoryInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        binding.tvReposName.text = repoName
        viewModel.getRepoInfo(
            repoName = repoName,
            userName = userName,
            authToken = sharedPreferences.token
        )
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        binding.rvContributorsList.adapter = contributorsListAdapter
        binding.rvIssuesList.adapter = issuesListAdapter
        binding.rvContributorsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIssuesList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeLiveData() {
        viewModel.repoInfoLiveData.observe(viewLifecycleOwner, { reposInfoResponse ->
            if (reposInfoResponse.isError) {
                Log.d("TAG111", "ERROR")
            } else {
                showReadme(reposInfoResponse.successResult.description)
            }
        })
        viewModel.contributorsLiveData.observe(viewLifecycleOwner, { contributorsList ->
            if (contributorsList.isError) {
                Log.d("TAG111", "ERROR")
            } else {
                contributorsListAdapter.updateContributorsList(contributorsList.successResult)
            }
        })
        viewModel.issuesLiveData.observe(viewLifecycleOwner, { issuesList ->
            if (issuesList.isError) {
                Log.d("TAG111", "ERROR")
            } else {
                issuesListAdapter.updateIssuesList(issuesList.successResult)
            }
        })

    }

    private fun showReadme(description: String?) {
        if (description == null) {
            binding.tvReadme.text = getString(R.string.text_readme_empty)
        } else {
            binding.tvReadmeText.text = description
        }

    }

    companion object {
        private const val KEY_REPOS_NAME = "KEY_REPOS_NAME"
        private const val KEY_USER_NAME = "KEY_USER_NAME"

        fun newInstance(reposName: String, userName: String): ReposInfoFragment =
            ReposInfoFragment().apply {
                val bundle = Bundle()
                bundle.putString(KEY_REPOS_NAME, reposName)
                bundle.putString(KEY_USER_NAME, userName)
                this.arguments = bundle
            }
    }
}