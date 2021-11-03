package com.xiaowugui.e_scimoc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xiaowugui.e_scimoc.model.Comics
import com.xiaowugui.e_scimoc.databinding.ComicsItemViewBinding


class ImageGridAdapter( private val onClickListener: OnClickListener ) : PagingDataAdapter<Comics, ImageGridAdapter.ImageGridAdapterViewHolder>(DiffCallback){

    class ImageGridAdapterViewHolder(private var binding: ComicsItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comics: Comics) {
            binding.comics = comics
            // force the data binding to execute immediately
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Comics>() {
        /*
            const for adapting the span used for the footer
         */
        const val NETWORK_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2

        override fun areItemsTheSame(oldItem: Comics, newItem: Comics): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comics, newItem: Comics): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGridAdapterViewHolder {
        return ImageGridAdapterViewHolder(ComicsItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * bind the clickListener on each image
     */
    override fun onBindViewHolder(holder: ImageGridAdapterViewHolder, position: Int) {
        val comics = getItem(position)
        if(comics != null){
            holder.itemView.setOnClickListener {
                onClickListener.onClick(comics)
            }
            holder.bind(comics)
        }
    }

    /**
     * return the type of view, if network then we are loading and
     * the view need to be on 2 span
     */
    override fun getItemViewType(position: Int): Int {
        if (position == itemCount){
            return NETWORK_VIEW_TYPE
        }else {
            return IMAGE_VIEW_TYPE
        }
    }


    class OnClickListener(val clickListener: (comics: Comics) -> Unit) {
        fun onClick(comics: Comics) = clickListener(comics)
    }

}