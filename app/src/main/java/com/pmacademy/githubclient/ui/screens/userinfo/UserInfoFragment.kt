package com.pmacademy.githubclient.ui.screens.userinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.Application
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.UserInfoFragmentBinding
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.screens.userinfo.adapter.ReposListAdapter
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@ExperimentalSerializationApi
class UserInfoFragment : BaseFragment(R.layout.user_info_fragment) {

    private val rvPostsAdapter =
        ReposListAdapter { reposName ->
            user.login?.let { login ->
                navigator.showProjectInfoFragment(reposName, login)
            }
        }
    private lateinit var binding: UserInfoFragmentBinding

    @Inject
    lateinit var viewModel: UserInfoViewModel
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
        viewModel.getUserReposList(user = user)
        initListeners()
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

    override fun showErrorMessage(@StringRes errorRes: Int) {
        binding.progressBarLoading.visibility = View.INVISIBLE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = getString(errorRes)
    }

    private fun observeReposLiveData() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, { reposList ->
            if (reposList.isError) {
                handleError(reposList.errorResult)
            } else {
                user.avatarUrl?.let { avatarUrl -> loadAvatar(avatarUrl) }
                showAllViewsHideProgressBar()
                updateReposList(reposList.successResult)
            }
        })
    }

    private fun showAllViewsHideProgressBar() {
        binding.tvUserName.visibility = View.VISIBLE
        binding.tvListRepositoriesText.visibility = View.VISIBLE
        binding.rvListRepositories.visibility = View.VISIBLE
        binding.ivUserAvatar.visibility = View.VISIBLE
        binding.btnSearch.visibility = View.VISIBLE

        binding.progressBarLoading.visibility = View.GONE
    }

    private fun initListeners() {
        binding.btnSearch.setOnClickListener {
            navigator.showUsersSearchFragment()
        }
    }

    companion object {
        private const val KEY_USER = "KEY_USER"

        fun newInstance(user: UserResponse): UserInfoFragment = UserInfoFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(KEY_USER, user)
            this.arguments = bundle
        }
    }

    override fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }
}
