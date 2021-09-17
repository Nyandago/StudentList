package com.cannybits.studentlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_items_student.view.*


class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList : ArrayList<StudentModel> = ArrayList()
    private var onClickItem : ((StudentModel)->Unit)? = null
    private var onClickDeleteItem : ((StudentModel)->Unit)? = null

    fun addItems(items: ArrayList<StudentModel>){
        this.stdList = items
         notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel)-> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel)-> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder (
      LayoutInflater.from(parent.context).inflate(R.layout.card_items_student,parent,false)
        )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(var view : View):RecyclerView.ViewHolder(view){
        private var id = view.tvId
        private var firstName = view.tvFirstName
        private var lastName = view.tvLastName
        private var email = view.tvEmail
        var btnDelete = view.btnDelete

        fun bindView(std: StudentModel){
            id.text = std.id.toString()
            firstName.text = std.firstName
            lastName.text = std.lastName
            email.text = std.email
        }
    }
}