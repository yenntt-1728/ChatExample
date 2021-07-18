package com.example.chatexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR

open class SimpleAdapter<T>(private val inflater: LayoutInflater, private val layoutId: Int) : BaseAdapter<T>() {

    var listener: SimpleDataBoundItemListener? = null

    override fun bind(holder: DataBoundViewHolder<ViewDataBinding>, item: T, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, item)
            listener?.let {
                setVariable(BR.listener, it)
            }
            executePendingBindings()
        }
    }

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, layoutId, parent, false)
    }
}

interface SimpleDataBoundItemListener
