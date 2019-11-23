package com.shibuyaxpress.petchaserkt.components.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.components.ReportDetailActivity
import com.shibuyaxpress.petchaserkt.components.recyclerview.OnItemClickListener
import com.shibuyaxpress.petchaserkt.components.recyclerview.ReportAdapter
import com.shibuyaxpress.petchaserkt.models.Report
import com.shibuyaxpress.petchaserkt.network.APIServiceGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ReportFragment : Fragment(), OnItemClickListener {

    private lateinit var adapter: ReportAdapter
    private lateinit var reportRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*return super.onCreateView(inflater, container, savedInstanceState)*/
        val rootView = inflater.inflate(R.layout.fragment_reports, container, false)
        initUIComponents(rootView)
        return rootView
    }

    private fun initUIComponents(rootView:View?) {
        reportRecyclerView = rootView!!.findViewById(R.id.recyclerReport)
        reportRecyclerView
            .layoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false)
        adapter = ReportAdapter(activity!!.applicationContext,this)
        reportRecyclerView.adapter = adapter
        getReportFromApi()
    }

    override fun onItemClicked(item: Any) {
        //Toast.makeText(activity!!.applicationContext, "$item",Toast.LENGTH_SHORT).show()
        activity.let {
            startActivity(Intent(activity,
                ReportDetailActivity::class.java)
                .putExtra("reportSelected",item as Report))
        }
    }

    private fun getReportFromApi() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = APIServiceGenerator.petchaserClient.getReportsAsync().await()
                if (webResponse.isSuccessful) {
                    val reportList: List<Report>? = webResponse.body()!!.data
                    Log.d(tag, reportList!!.toString())
                    adapter.setReportList(reportList)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e(tag, "Error ${webResponse.code()}")
                    Toast.makeText(activity!!.applicationContext, "Error ${webResponse.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(tag, "Exception"+e.printStackTrace())
                Toast.makeText(activity!!.applicationContext, "Error ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        fun newInstance():ReportFragment = ReportFragment()
    }
}