package com.andyshon.cryptocoins.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.ui.holders.MainVH
import com.andyshon.cryptocoins.ui.base.recycler.ItemClickListener

class MainAdapter(
    private var items: ArrayList<Coin>,
    private val listener: ItemClickListener<Coin>
) : RecyclerView.Adapter<MainVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH {
        return MainVH(parent).apply {
            itemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition, items[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainVH, position: Int) = holder.bind(items[position])

}