package com.shibuyaxpress.petchaserkt.components.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.shibuyaxpress.petchaserkt.R
import com.shibuyaxpress.petchaserkt.models.Report
import com.shibuyaxpress.petchaserkt.modules.GlideApp

class ReportAdapter(context: Context, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ReportHolder>() {

    private var context: Context? = null
    private var reportList: List<Report>? = ArrayList()

    init {
        this.context = context
    }

    fun setReportList(list:List<Report>) {
        this.reportList = list
    }

    override fun getItemCount(): Int {
        return if (reportList!!.isNotEmpty()) {
            reportList!!.size
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item_report, parent, false)
        return ReportHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReportHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.d("REPORT ADAPTER", "me clickaron")
        }
        holder.bind(reportList?.get(position), itemClickListener)
    }
}
interface OnItemClickListener {
    fun onItemClicked(item: Any)
}