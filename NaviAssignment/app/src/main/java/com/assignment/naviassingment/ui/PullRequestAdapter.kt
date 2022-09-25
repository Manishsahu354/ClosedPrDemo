package com.assignment.naviassingment.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.naviassingment.R
import com.assignment.naviassingment.data.model.PullRequestModel
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PullRequestAdapter(
    var responseList: MutableList<PullRequestModel>,
    var mContext:Context
): RecyclerView.Adapter<PullRequestAdapter.CustomerMenuViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerMenuViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.closed_pr_item_layout,parent,false)
        return CustomerMenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerMenuViewHolder, position: Int) {
        val response = responseList[position]
        holder.apply {
            Glide.with(mContext).load(response.user.userImage).into(ivUserImage)
            tvTitle.text = response.title
            tvUserName.text = response.user.userName
            tvCreatedDate.text = response.createdDate?.split("T")?.get(0) ?: response.createdDate
            tvClosedDate.text =  response.closedDate?.split("T")?.get(0)  ?: response.closedDate
        }
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    fun setData(responseList: MutableList<PullRequestModel>) {
        this.responseList = responseList
        notifyDataSetChanged()
    }

    class CustomerMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivUserImage: ImageView = itemView.findViewById(R.id.ivUserImage)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvCreatedDate: TextView = itemView.findViewById(R.id.tvCreatedDate)
        val tvClosedDate: TextView = itemView.findViewById(R.id.tvClosedDate)
    }
}