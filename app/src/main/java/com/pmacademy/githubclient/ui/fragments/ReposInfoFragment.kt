package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.RepositoryInfoFragmentBinding
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.adapter.ContributorsAdapter
import com.pmacademy.githubclient.ui.adapter.IssueAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.ReposInfoViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class ReposInfoFragment : BaseFragment(R.layout.repository_info_fragment) {

    private lateinit var repoName: String
    private lateinit var userName: String
    private lateinit var binding: RepositoryInfoFragmentBinding
    private val viewModel: ReposInfoViewModel by viewModels()
    private val contributorsAdapter = ContributorsAdapter { user ->
        navigator.showUserInfoFragment(user)
    }
    private val issuesAdapter = IssueAdapter { issue ->
        navigator.showIssueInfoFragment(
            issue = issue,
            userName = userName,
            repoName = repoName
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        repoName = requireArguments().getString(KEY_REPO_NAME).toString()
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
        binding.rvContributorsList.adapter = contributorsAdapter
        binding.rvIssuesList.adapter = issuesAdapter
        binding.rvContributorsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIssuesList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeLiveData() {
        viewModel.repoInfoLiveData.observe(viewLifecycleOwner, { repoInfoModel ->
            if (!repoInfoModel.isError) {
                showAllViewsHideProgressBar()
                showReadme(repoInfoModel.successResult.readmeText)
                showContributorsList(repoInfoModel.successResult.contributorsList)
                showIssuesList(repoInfoModel.successResult.issuesList)
            } else {
                handleError(repoInfoModel.errorResult)
            }
        })
    }

    override fun showErrorMessage(errorRes: Int) {
        binding.progressBarLoading.visibility = View.GONE
        binding.tvEmptyRepoCaption.visibility = View.VISIBLE
    }

    private fun showIssuesList(issuesList: List<IssueResponse>) {
        if (issuesList.isNotEmpty()) {
            issuesAdapter.updateIssuesList(issuesList)
            binding.tvEmptyIssuesList.visibility = View.GONE
            binding.rvIssuesList.visibility = View.VISIBLE
        }
    }

    private fun showContributorsList(contributorsList: List<UserResponse>) {
        if (contributorsList.isNotEmpty()) {
            contributorsAdapter.updateContributorsList(contributorsList)
            binding.tvEmptyContributorsList.visibility = View.GONE
            binding.rvContributorsList.visibility = View.VISIBLE
        }
    }

    private fun showAllViewsHideProgressBar() {
        binding.tvReadme.visibility = View.VISIBLE
        binding.tvReposName.visibility = View.VISIBLE
        binding.tvReadmeText.visibility = View.VISIBLE
        binding.tvContributorsListText.visibility = View.VISIBLE
        binding.tvIssuesListText.visibility = View.VISIBLE

        binding.tvEmptyIssuesList.visibility = View.VISIBLE
        binding.tvEmptyContributorsList.visibility = View.VISIBLE

        binding.progressBarLoading.visibility = View.GONE
    }

    private fun showReadme(readme: String) {
        if (readme.isNotEmpty()) {
            binding.tvReadme.text = readme
            binding.tvReadme.movementMethod = ScrollingMovementMethod()
            binding.tvReadme.gravity = Gravity.START
        }
    }

    companion object {
        private const val KEY_REPO_NAME = "KEY_REPOS_NAME"
        private const val KEY_USER_NAME = "KEY_USER_NAME"

        fun newInstance(reposName: String, userName: String): ReposInfoFragment =
            ReposInfoFragment().apply {
                val bundle = Bundle()
                bundle.putString(KEY_REPO_NAME, reposName)
                bundle.putString(KEY_USER_NAME, userName)
                this.arguments = bundle
            }
    }
}
