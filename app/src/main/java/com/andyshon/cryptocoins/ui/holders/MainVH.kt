package com.andyshon.cryptocoins.ui.holders

import android.view.ViewGroup
import com.andyshon.cryptocoins.BuildConfig
import com.andyshon.cryptocoins.R
import com.andyshon.cryptocoins.data.entity.Coin
import com.andyshon.cryptocoins.ui.base.recycler.BaseVH
import com.andyshon.cryptocoins.utils.extensions.decFormat
import com.andyshon.cryptocoins.utils.extensions.loadImage
import com.andyshon.cryptocoins.utils.extensions.setColouredText
import kotlinx.android.synthetic.main.item_coin.view.*
import java.util.*

class MainVH(parent: ViewGroup) : BaseVH<Coin>(parent, R.layout.item_coin) {

    override fun bind(item: Coin) {
        val usd = item.quote.usd
        itemView.name.text = item.name
        itemView.symbol.text = item.symbol
        itemView.price.decFormat("$#.###", usd.price)
        itemView.hr1.setColouredText("hr1: ", "#.##%", usd.percentChange1h)
        itemView.hr24.setColouredText("hr24: ", "#.##%", usd.percentChange24h)
        itemView.d7.setColouredText("d7: ", "#.##%", usd.percentChange7d)
        val imageUrl = BuildConfig.API_CLOUD_URL.plus(item.symbol.toLowerCase(Locale.getDefault()) + ".png")
        itemView.image.loadImage(imageUrl)
    }
}