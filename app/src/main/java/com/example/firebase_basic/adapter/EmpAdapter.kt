package com.example.firebase_basic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.firebase_basic.model.EmployeeModel
import com.example.firebase_basic.R


class EmpAdapter(private val employeeList: MutableList<EmployeeModel>) : RecyclerView.Adapter<EmpAdapter.mViewHolder>() {

    class mViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {

         val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)

        return mViewHolder(view)
    }


    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        val currentItem = employeeList[position]
         holder.itemView.findViewById<TextView>(R.id.EmpName).text = currentItem.name
         holder.itemView.findViewById<TextView>(R.id.EmpEmail).text = currentItem.email
         holder.itemView.findViewById<TextView>(R.id.EmpAge).text = currentItem.age.toString()

    }

    override fun getItemCount(): Int {
       return employeeList.size
    }


    fun updateData() {
        employeeList.clear()
        notifyDataSetChanged()
    }

}