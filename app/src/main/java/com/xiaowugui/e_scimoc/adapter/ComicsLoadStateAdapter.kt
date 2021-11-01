package com.xiaowugui.e_scimoc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xiaowugui.e_scimoc.R
import com.xiaowugui.e_scimoc.databinding.LoadStateFooterItemViewBinding

/**
 * Adapter for the recyclerView's footer
 */
class ComicsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ComicsLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        private val binding: LoadStateFooterItemViewBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            // set the visibility of all elements
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_footer_item_view, parent, false)
        val binding = LoadStateFooterItemViewBinding.bind(view)
        return LoadStateViewHolder(binding, retry)
    }
}