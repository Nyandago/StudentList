package com.cannybits.studentlist

import kotlin.random.Random

data class StudentModel (
    var id: Int = getAutoId(),
    var firstName: String,
    var lastName: String,
    var email: String
){
    companion object{
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }
}