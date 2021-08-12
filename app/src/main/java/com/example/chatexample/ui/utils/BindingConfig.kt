package com.example.chatexample.ui.utils

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.chatexample.R
import java.text.SimpleDateFormat

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

    @BindingAdapter("time")
    @JvmStatic
    fun TextView.setTime(time : Long) {
        val sdf = SimpleDateFormat("HH:mm")
        text = sdf.format(time)
    }
}