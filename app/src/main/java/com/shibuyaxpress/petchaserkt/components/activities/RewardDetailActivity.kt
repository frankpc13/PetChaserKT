package com.shibuyaxpress.petchaserkt.components.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Reward
import com.shibuyaxpress.petchaserkt.modules.GlideApp
import kotlinx.android.synthetic.main.activity_reward_detail.*

class RewardDetailActivity : AppCompatActivity() {

    var reward: Reward? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_detail)
        reward = intent.getParcelableExtra("rewardSelected")
        initUI(reward!!)
    }

    private fun initUI(reward: Reward){
        GlideApp.with(this).load(reward.image).into(imageReward)
        categoryReward.text = reward.category!!.name
        nameReward.text = reward.name
    }
}
