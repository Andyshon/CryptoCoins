package com.andyshon.cryptocoins.ui.base.recycler

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.andyshon.cryptocoins.utils.extensions.inflate

abstract class BaseVH<I>(parent: ViewGroup, @LayoutRes id: Int) : RecyclerView.ViewHolder(parent.inflate(id)) {

    abstract fun bind(item: I)
}