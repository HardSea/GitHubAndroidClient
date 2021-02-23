package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.UserInfoFragmentBinding
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.adapter.ReposListAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.UserInfoViewModel
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserInfoFragment : BaseFragment(R.layout.user_info_fragment) {

    private val rvPostsAdapter =
        ReposListAdapter { reposName -> navigator.showProjectInfoFragment(reposName, user.login) }
    private lateinit var binding: UserInfoFragmentBinding
    private val viewModel: UserInfoViewModel by viewModels()
    private lateinit var user: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = requireArguments().getSerializable(KEY_USER) as UserResponse
        binding = UserInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeReposLiveData()
        initRecyclerView()
        binding.tvUserName.text = user.login
        viewModel.getUserReposList(user = user, authToken = sharedPreferences.token)

    }

    private fun loadAvatar(imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .circleCrop()
            .into(binding.ivUserAvatar)
    }

    private fun initRecyclerView() {
        binding.rvListRepositories.adapter = rvPostsAdapter
        binding.rvListRepositories.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateReposList(items: List<ReposResponse>) {
        if (items.isNotEmpty()) {
            rvPostsAdapter.updateReposList(items)
        } else {
            showEmptyReposTitle()
        }
    }

    private fun showEmptyReposTitle() {
        binding.tvEmptyReposListCaption.visibility = View.VISIBLE
        binding.rvListRepositories.visibility = View.INVISIBLE
    }

    private fun showErrorMessage(error: String) {
        binding.progressBarLoading.visibility = View.INVISIBLE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = error
    }

    private fun observeReposLiveData() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, { reposList ->
            if (!reposList.isLoading) {
                if (reposList.isError) {
                    handleError(reposList.errorResult)
                } else {
                    loadAvatar(user.avatarUrl)
                    showAllViewsHideProgressBar()
                    updateReposList(reposList.successResult)
                }
            }
        })
    }

    private fun handleError(errorResult: GithubError) {
        when (errorResult) {
            GithubError.UNAUTHORIZED -> {
                Toast.makeText(requireContext(), "Need authorization", Toast.LENGTH_SHORT).show()
                navigator.showLoginFragment()
            }
            else -> showErrorMessage(errorResult.toString())
        }
    }


    private fun showAllViewsHideProgressBar() {
        binding.tvUserName.visibility = View.VISIBLE
        binding.tvListRepositoriesText.visibility = View.VISIBLE
        binding.rvListRepositories.visibility = View.VISIBLE
        binding.ivUserAvatar.visibility = View.VISIBLE

        binding.progressBarLoading.visibility = View.GONE
    }

    companion object {
        private const val KEY_USER = "KEY_USER"

        fun newInstance(user: UserResponse): UserInfoFragment = UserInfoFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(KEY_USER, user)
            this.arguments = bundle
        }
    }
}
