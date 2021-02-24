package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.IssueCommentResponse
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.databinding.IssueInfoFragmentBinding
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.adapter.IssueCommentsAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.IssueCommentsViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class IssueInfoFragment : BaseFragment(R.layout.issue_info_fragment) {

    private lateinit var binding: IssueInfoFragmentBinding
    private val viewModel: IssueCommentsViewModel by viewModels()
    private lateinit var issue: IssueResponse
    private lateinit var userName: String
    private lateinit var repoName: String
    private val rvIssueCommentsAdapter = IssueCommentsAdapter { commentResponse, clickType ->
        viewModel.createCommentReaction(
            userName = userName,
            repoName = repoName,
            commentResponse = commentResponse,
            authToken = sharedPreferences.token,
            clickType = clickType
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        issue = requireArguments().getSerializable(KEY_ISSUE) as IssueResponse
        userName = requireArguments().getString(KEY_USER_NAME).toString()
        repoName = requireArguments().getString(KEY_REPO_NAME).toString()
        binding = IssueInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeIssueCommentsLiveData()
        viewModel.getIssueComments(
            userName = userName,
            repoName = repoName,
            issueNumber = issue.number,
            authToken = sharedPreferences.token
        )
        setupView()
    }

    private fun setupView() {
        binding.tvIssueAuthor.text = userName
        binding.tvIssueTitle.text = issue.title
        binding.tvIssueBody.text = issue.body
        binding.tvIssueBody.movementMethod = ScrollingMovementMethod()
    }

    private fun observeIssueCommentsLiveData() {
        viewModel.issueCommentsLiveData.observe(viewLifecycleOwner, { issueComments ->
            if (!issueComments.isError) {
                showAllViewsHideProgressBar()
                if (issueComments.successResult.isNotEmpty()) {
                    showIssueComments(issueComments.successResult)
                }
            } else {
                handleError(issueComments.errorResult)
            }
        })

        viewModel.createReactionResultLiveData.observe(viewLifecycleOwner, { createReactionResult ->
            if (createReactionResult.isError) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.create_reaction_fail_text),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.create_reaction_success_text),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }

    private fun handleError(errorResult: GithubError) {
        if (errorResult == GithubError.UNAUTHORIZED) {
            navigator.showLoginFragment()
            Toast.makeText(
                requireContext(),
                getString(R.string.need_authorization_text),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.error_get_issue_comments_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showAllViewsHideProgressBar() {
        binding.tvIssueBody.visibility = View.VISIBLE
        binding.tvIssueTitle.visibility = View.VISIBLE
        binding.tvIssueAuthor.visibility = View.VISIBLE
        binding.tvEmptyIssueCommentsText.visibility = View.VISIBLE

        binding.progressBarLoading.visibility = View.GONE
    }

    private fun showIssueComments(commentsList: List<IssueCommentResponse>) {
        binding.tvEmptyIssueCommentsText.visibility = View.GONE
        binding.rvIssueComments.visibility = View.VISIBLE
        rvIssueCommentsAdapter.updateIssuesList(commentsList)
    }

    private fun initRecyclerView() {
        binding.rvIssueComments.adapter = rvIssueCommentsAdapter
        binding.rvIssueComments.layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
        val drawableRes = getDrawable(requireContext(), R.drawable.divider)
        if (drawableRes != null) {
            itemDecoration.setDrawable(drawableRes)
            binding.rvIssueComments.addItemDecoration(itemDecoration)
        }
    }

    companion object {
        private const val KEY_ISSUE = "KEY_ISSUE"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_REPO_NAME = "KEY_REPO_NAME"

        fun newInstance(
            issue: IssueResponse,
            userName: String,
            repoName: String
        ): IssueInfoFragment =
            IssueInfoFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(KEY_ISSUE, issue)
                bundle.putString(KEY_USER_NAME, userName)
                bundle.putString(KEY_REPO_NAME, repoName)
                this.arguments = bundle
            }
    }
}