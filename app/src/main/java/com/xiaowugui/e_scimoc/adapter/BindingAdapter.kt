package com.xiaowugui.e_scimoc.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xiaowugui.e_scimoc.R

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