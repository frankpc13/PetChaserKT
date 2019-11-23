package com.shibuyaxpress.petchaserkt.components.recyclerview


import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Reward
import com.shibuyaxpress.petchaserkt.modules.GlideApp

class RewardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView? = null
    var price: TextView? = null
    var category: TextView? = null
    var imageReward: ImageView? = null
    var card: CardView? = null

    init {
        title = itemView.findViewById(R.id.titleReward)
        price = itemView.findViewById(R.id.PointsReward)
        category = itemView.findViewById(R.id.categoryReward)
        imageReward = itemView.findViewById(R.id.imageReward)
        card = itemView.findViewById(R.id.cardReward)
    }
    fun bind(reward: Reward?, clickListener: OnItemClickListener) {
        title!!.text = reward!!.name
        price!!.text = "${reward.price} ptos."
        category!!.text = reward.category!!.name
        GlideApp.with(itemView).load(reward.image)
            .centerCrop()
            .into(imageReward!!)
        card!!.setOnClickListener {
            Log.d("CLICK ON REWARD CARD", "$reward")
            clickListener.onItemClicked(reward)
        }

    }
}