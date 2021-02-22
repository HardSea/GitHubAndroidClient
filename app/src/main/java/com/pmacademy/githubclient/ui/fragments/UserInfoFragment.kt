package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        setUserName()
        viewModel.getUserReposList(user = user, authToken = sharedPreferences.token)

    }

    private fun setUserName() {
        binding.tvUserName.text = user.login
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
        rvPostsAdapter.updateReposList(items)
    }

    private fun showErrorMessage(error: GithubError) {
        binding.rvListRepositories.visibility = View.GONE
        binding.ivUserAvatar.visibility = View.GONE
        binding.tvUserName.visibility = View.GONE
        binding.tvListRepositoriesText.visibility = View.GONE

        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = error.name
    }

    private fun observeReposLiveData() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            if (!it.isLoading) {
                if (it.isError) {
                    showErrorMessage(it.errorResult)
                } else {
                    updateReposList(it.successResult)
                    loadAvatar(user.avatarUrl)
                    showAllViewsHideProgressBar()
                }
            }
        })
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
