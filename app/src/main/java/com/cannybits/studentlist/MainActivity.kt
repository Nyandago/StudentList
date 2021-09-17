package com.cannybits.studentlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var edFirstName : EditText
    private lateinit var edLastName : EditText
    private lateinit var edEmail : EditText
    private lateinit var btnAdd : Button
    private lateinit var btnViewList : Button

    private lateinit var sqliteHelper: MySQLHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = MySQLHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnViewList.setOnClickListener { getStudents() }
    }

    private fun getStudents() {
        val stdList = sqliteHelper.getAllStudents()
         Log.e("pppp", "${stdList.size}")

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
        edFirstName = findViewById(R.id.etFirstName)
        edLastName = findViewById(R.id.etLastName)
        edEmail = findViewById(R.id.etEmail)
        btnAdd = findViewById(R.id.btnAddStudent)
        btnViewList = findViewById(R.id.btnViewStudentList)
        recyclerView = findViewById(R.id.rvStudentList)
    }
}