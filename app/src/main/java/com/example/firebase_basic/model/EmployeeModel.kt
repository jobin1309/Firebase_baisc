package com.example.firebase_basic.model

import com.google.firebase.database.PropertyName


data class EmployeeModel(
   @PropertyName("uid") var uid: String? = null,
   @PropertyName("name") var name: String? = null,
   @PropertyName("email")var email: String? = null,
   @PropertyName("age") var age: Int? = null
) {
}