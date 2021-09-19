package com.cannybits.studentlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var edFirstName : EditText
    private lateinit var edLastName : EditText
    private lateinit var edEmail : EditText
    private lateinit var btnAdd : Button
    private lateinit var btnViewList : Button
    private lateinit var btnFullScreenList : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqliteHelper: MySQLHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = MySQLHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnViewList.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }
        btnFullScreenList.setOnClickListener { /* call student list xml*/ }

        adapter?.setOnClickItem { Toast.makeText(this,it.lastName+", "+it.firstName,Toast.LENGTH_SHORT).show()

            //to update an item
            etFirstName.setText(it.firstName)
            etLastName.setText(it.lastName)
            etEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun getStudents() {
        val stdList = sqliteHelper.getAllStudents()

        //Display Students list in Recycler view
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val firstName = edFirstName.text.toString()
        val lastName = edLastName.text.toString()
        val email = edEmail.text.toString()

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Please Enter all details",Toast.LENGTH_LONG).show()
        } else{
            val student = StudentModel(firstName=firstName,lastName = lastName,email = email)
            val status = sqliteHelper.insertStudent(student)

            //check if successfully added
            if(status > -1){
                Toast.makeText(this,"Student Added ...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else{
                Toast.makeText(this,"Record Not Saved", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun updateStudent(){
        val firstName = etFirstName.text.toString()
        val lastName = etLastName.text.toString()
        val email = etEmail.text.toString()

        //check if record is not changed
        if(firstName == std?.firstName && lastName == std?.lastName && email == std?.email){
            Toast.makeText(this, "Record Not changed ...",Toast.LENGTH_SHORT).show()
            return
        }

        if(std == null) return

        val std = StudentModel(id = std!!.id, firstName = firstName, lastName = lastName, email = email)
        val status = sqliteHelper.updateStudent(std)
        if(status > -1){
            clearEditText()
            getStudents()
        } else{
            Toast.makeText(this,"Student not updated...",Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(id: Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this student?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){
            dialog,_ ->
            sqliteHelper.deleteStudentById(id)
            getStudents()
            clearEditText()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){
                dialog,_ -> dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edFirstName.setText("")
        edLastName.setText("")
        edEmail.setText("")
        edFirstName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edFirstName = etFirstName
        edLastName = etLastName
        edEmail = etEmail
        btnAdd = btnAddStudent
        btnViewList = btnViewStudentList
        btnUpdate = btnUpdateStudent
        recyclerView = rvStudentList
        btnFullScreenList = btnFullList
    }
}