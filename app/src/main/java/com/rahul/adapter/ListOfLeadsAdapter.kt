package com.rahul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rahul.response.Test
import com.rahul.salescrm.R


class ListOfLeadsAdapter(): RecyclerView.Adapter<ListOfLeadsAdapter.LeadsViewHolder>() {

    inner class LeadsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val leadName = view.findViewById<TextView>(R.id.name_of_leads)
    }

    private val differCallBack=object : DiffUtil.ItemCallback< Test>(){

        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem == newItem

        }

    }

    private val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.leads_rv, parent, false)

        return LeadsViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeadsViewHolder, position: Int) {
       val leadsList = differ.currentList[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(leadsList) }
        }
        holder.leadName.text = "Rahul C Ravi"

    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }


    private var onItemClickListener:((Test)->Unit)?=null

    fun setOnClickListener(listener:(Test)->Unit){
        onItemClickListener=listener
    }
//todo change test to another
//todo change test to another
//todo change test to another
//todo change test to another
//todo change test to another
}