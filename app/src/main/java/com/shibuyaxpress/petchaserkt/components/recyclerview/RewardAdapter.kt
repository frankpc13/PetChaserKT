package com.shibuyaxpress.petchaserkt.components.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Reward
import com.shibuyaxpress.petchaserkt.modules.GlideApp

class RewardAdapter(context:Context, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RewardHolder>() {

    private var context: Context? = null
    private var rewardList: List<Reward>? = ArrayList()

    init {
        this.context = context

    }

    fun setRewardList(list:List<Reward>) {
        this.rewardList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item_reward, parent, false)
        return RewardHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (rewardList!!.isNotEmpty()){
            rewardList!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: RewardHolder, position: Int) {
        holder.bind(rewardList!![position], itemClickListener)
    }

}