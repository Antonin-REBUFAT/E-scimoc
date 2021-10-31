package com.xiaowugui.e_scimoc.adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xiaowugui.e_scimoc.R
import com.xiaowugui.e_scimoc.model.ApiStatus
import com.xiaowugui.e_scimoc.model.Comics

/**
 * Binding for the comics' cover image
 */
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let {
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.broken_img)
            )
            .into(imageView)
    }
}

/**
 * Binding for the recyclerView
 */
@BindingAdapter("listComics")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Comics>?){
    val adapter = recyclerView.adapter as ImageGridAdapter
    adapter.submitList(data)
}

/**
 * Binding for the retrieving status
 */
@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?){
    when(status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}