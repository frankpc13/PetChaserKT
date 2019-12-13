package com.shibuyaxpress.petchaserkt.components.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.components.activities.RewardDetailActivity
import com.shibuyaxpress.petchaserkt.components.recyclerview.OnItemClickListener
import com.shibuyaxpress.petchaserkt.components.recyclerview.RewardAdapter
import com.shibuyaxpress.petchaserkt.models.Reward
import com.shibuyaxpress.petchaserkt.network.APIServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "Reward Fragment"
class RewardFragment : Fragment(), OnItemClickListener {

    private lateinit var adapter: RewardAdapter
    private lateinit var rewardRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_reward, container, false)
        initUIComponents(rootView)
        return rootView
    }

    private fun initUIComponents(rootView: View?) {
        rewardRecyclerView = rootView!!.findViewById(R.id.recyclerReward)
        rewardRecyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext,2,GridLayoutManager.VERTICAL, false)
        adapter = RewardAdapter(activity!!.applicationContext, this)
        rewardRecyclerView.adapter = adapter
        getRewardsFromApi()
    }

    override fun onItemClicked(item: Any) {
        Toast.makeText(activity,"${item as Reward}",Toast.LENGTH_SHORT).show()
        activity.let {
            startActivity(Intent(activity, RewardDetailActivity::class.java).putExtra("rewardSelected",
                item
            ))
        }
    }

    private fun getRewardsFromApi() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = APIServiceGenerator.petchaserClient.getRewardsAsync().await()
                if (webResponse.isSuccessful) {
                    val rewardList = webResponse.body()!!.data
                    Log.d(TAG, rewardList!!.toString())
                    adapter.setRewardList(rewardList)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e(TAG, "error ${webResponse.code()}")
                    Toast
                        .makeText(activity!!.applicationContext,
                            "Error ${webResponse.errorBody()}",
                            Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception) {
                Log.e(TAG, "Exception"+e.printStackTrace())
                Toast.makeText(activity!!.applicationContext, "Error ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newInstance():RewardFragment = RewardFragment()
    }
}