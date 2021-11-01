package com.xiaowugui.e_scimoc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xiaowugui.e_scimoc.model.Comics
import com.xiaowugui.e_scimoc.databinding.ComicsItemViewBinding

class ImageGridAdapter( private val onClickListener: OnClickListener ) : ListAdapter<Comics, ImageGridAdapter.ImageGridAdapterViewHolder>(DiffCallback){

    class ImageGridAdapterViewHolder(private var binding: ComicsItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comics: Comics) {
            binding.comics = comics
            // force the data binding to execute immediately
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Comics>() {
        override fun areItemsTheSame(oldItem: Comics, newItem: Comics): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comics, newItem: Comics): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGridAdapter.ImageGridAdapterViewHolder {
        return ImageGridAdapterViewHolder(ComicsItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ImageGridAdapter.ImageGridAdapterViewHolder, position: Int) {
        val comics = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(comics)
        }
        holder.bind(comics)
    }

    class OnClickListener(val clickListener: (comics: Comics) -> Unit) {
        fun onClick(comics: Comics) = clickListener(comics)
    }

}