package com.shibuyaxpress.petchaserkt.components.recyclerview

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Report
import com.shibuyaxpress.petchaserkt.modules.GlideApp

class ReportHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var name: TextView? = null
    var image: ImageView? = null
    var address: TextView? = null
    var card: CardView? = null
    init {
        name = itemView.findViewById(R.id.nameReport)
        image = itemView.findViewById(R.id.imageReport)
        address = itemView.findViewById(R.id.addressReport)
        card = itemView.findViewById(R.id.card_report)
    }

    fun bind(report: Report?, clickListener: OnItemClickListener ) {
        name!!.text = report!!.pet!!.name
        address!!.text = report.location
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        GlideApp.with(itemView).load(report.pet!!.image).apply(requestOptions)
            .apply(RequestOptions.circleCropTransform()).into(image!!)
        card!!.setOnClickListener {
            Log.d("AMTENT","DDDDD")
            clickListener.onItemClicked(report)
        }
        itemView.setOnClickListener {
            clickListener.onItemClicked(report)
        }
    }
}
