package com.example.firebase_basic.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase_basic.R
import com.example.firebase_basic.adapter.EmpAdapter
import com.example.firebase_basic.model.EmployeeModel
import com.example.firebase_basic.databinding.FragmentFetchDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class FetchDataFragment : Fragment() {

    private lateinit var mAdapter: EmpAdapter
    private lateinit var userRecylerview: RecyclerView
    private lateinit var userList: ArrayList<EmployeeModel>
    private lateinit var binding: FragmentFetchDataBinding
    private lateinit var auth: FirebaseAuth;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFetchDataBinding.inflate(layoutInflater, container, false)


        auth = FirebaseAuth.getInstance()
        var currentUserId = auth.currentUser?.uid
        userList = arrayListOf<EmployeeModel>()

        userRecylerview = binding.recyclerView

        userRecylerview.layoutManager = LinearLayoutManager(context)
        userRecylerview.setHasFixedSize(true)
        if (currentUserId != null) {
            getEmployeeData(currentUserId)
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.deleting_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fav_delete) {
            deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        var currentUserId = auth.currentUser?.uid
        val employessRef =
            currentUserId?.let {
                FirebaseDatabase.getInstance().getReference("Employees").child(it)
            }
        employessRef?.removeValue()
            ?.addOnCompleteListener() {
                Toast.makeText(context, "Data All cleared", Toast.LENGTH_SHORT)
            }
            ?.addOnFailureListener() {

            }

    }


    private fun getEmployeeData(uid: String) {

        val employessRef = FirebaseDatabase.getInstance().getReference("Employees")
        val userEmployeeRef = employessRef.child(uid ?: "")

        userEmployeeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapShot in snapshot.children) {
                        val user = userSnapShot.getValue(EmployeeModel::class.java)
                        userList.add(user!!)
                    }

                    mAdapter = EmpAdapter(userList)
                    userRecylerview.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


//        val empList = mutableListOf<EmployeeModel>()
//        val dbref = FirebaseDatabase.getInstance().getReference("Employees")
//
//        val query = dbref.orderByChild("uid").equalTo(uid)
//
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                empList.clear()
//                if (snapshot.exists()) {
//                    for (empSnap in snapshot.children) {
//                        val empData = empSnap.getValue(EmployeeModel::class.java)
//                        empData?.let {
//                            empList.add(empData)
//                        }
//                    }
//                    mAdapter = EmpAdapter(empList)
//                    binding.recyclerView.adapter = mAdapter
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error condition if necessary
//            }
//        })
//    }


    }

//                }
//            }

//            override fun onCancelled(error: DatabaseError) {
//                // Handle the error case
//                // ...
//            }
//        })
//    }


//
//    }
}



