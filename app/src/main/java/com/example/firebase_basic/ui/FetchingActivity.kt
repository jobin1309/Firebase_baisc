package com.example.firebase_basic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase_basic.adapter.EmpAdapter
import com.example.firebase_basic.model.EmployeeModel
import com.example.firebase_basic.databinding.ActivityFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingActivity : AppCompatActivity() {




    private lateinit var binding: ActivityFetchingBinding
    private lateinit var empList: MutableList<EmployeeModel>
    private lateinit var mAdapter: EmpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        empList = mutableListOf<EmployeeModel>()

        binding.recyclerView2.layoutManager = LinearLayoutManager(this)

        getEmployeeData()

    }

    private fun getEmployeeData() {

        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        val userRef = uid?.let {
            FirebaseDatabase.getInstance().getReference("Employees").child(
                it
            )
        }


        userRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()) {
                    try {
                        for (userSnapShot in snapshot.children) {
                            val user = userSnapShot.getValue(EmployeeModel::class.java)
                            if (user != null) {
                                empList.add(user)
                            }
                        }

                        mAdapter = EmpAdapter(empList)
                        binding.recyclerView2.adapter = mAdapter

                    } catch (e: Exception) {
                        Log.d("fetchingError", e.message.toString())
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}