package com.pipay.myfeed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dev.chrisbanes.insetter.doOnApplyWindowInsets
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val viewModel by viewModels<FeedViewModel>()
    private val adapter: FeedListAdapter = FeedListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = it
        })

        recyclerView.also {
            it.adapter = adapter
            it.layoutManager =
                GridLayoutManager(requireContext(), resources.getInteger(R.integer.post_span))
        }

        viewModel.posts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataResult.Success<List<Post>> -> adapter.submitList(it.value)
                is DataResult.Failure<List<Post>> -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


        viewModel.fetchPosts(0, 100)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchPosts(0, 100)
        }

        recyclerView.doOnApplyWindowInsets { view, insets, initialState ->
            view.updatePadding(
                top = initialState.paddings.top + insets.systemWindowInsetTop,
                bottom = initialState.paddings.bottom
                        + insets.systemWindowInsetBottom,
                left = initialState.paddings.left + insets.systemWindowInsetLeft,
                right = initialState.paddings.right + insets.systemWindowInsetRight
            )
        }
    }


    companion object {
        fun newInstance() = FeedFragment()
    }
}