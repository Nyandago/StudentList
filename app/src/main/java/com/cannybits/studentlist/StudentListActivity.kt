package com.cannybits.studentlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_student_list.*


class StudentListActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: MySQLHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnGoHome : Button
    private var adapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        initView()
        initRecyclerView()
        sqliteHelper = MySQLHelper(this)
        getStudents()

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }

        btnGoHome.setOnClickListener {
            val intent = Intent(this@StudentListActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView(){
        recyclerView = rvFullStudentList
        btnGoHome = btnBackHome
    }

    private fun getStudents(){
        val allStudents = sqliteHelper.getAllStudents()
        adapter?.addItems(allStudents)
    }

    private fun deleteStudent(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this student?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){
                dialog,_ ->
            sqliteHelper.deleteStudentById(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){
                dialog,_ -> dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

}