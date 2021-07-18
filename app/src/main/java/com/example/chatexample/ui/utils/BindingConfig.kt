package com.example.chatexample.ui.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.chatexample.R

object BindingConfig {
    @BindingAdapter("app:loadImage")
    @JvmStatic
    fun AppCompatImageView.loadImage(url: String?) {
        url?.let {
            Glide.with(this)
                .load(url)
                .circleCrop()
                .skipMemoryCache(false)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(this)
        }
    }

}