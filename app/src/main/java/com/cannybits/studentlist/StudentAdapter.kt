package com.cannybits.studentlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList : ArrayList<StudentModel> = ArrayList()

    fun addItems(items: ArrayList<StudentModel>){
        this.stdList = items
         notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder (
      LayoutInflater.from(parent.context).inflate(R.layout.card_items_student,parent,false)
        )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(var view : View):RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var firstName = view.findViewById<TextView>(R.id.tvFirstName)
        private var lastName = view.findViewById<TextView>(R.id.tvLastName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: StudentModel){
            id.text = std.id.toString()
            firstName.text = std.firstName
            lastName.text = std.lastName
            email.text = std.email
        }
    }
}