package com.example.firebase_basic.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebase_basic.R
import com.example.firebase_basic.model.EmployeeModel
import com.example.firebase_basic.databinding.FragmentInsertingDataFrgmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class InsertingDataFragment : Fragment() {

    private lateinit var binding: FragmentInsertingDataFrgmentBinding
    private lateinit var auth: FirebaseAuth;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = FragmentInsertingDataFrgmentBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()

        binding.viewBtn.setOnClickListener {
            val intent = Intent(context, FetchingActivity::class.java)
            startActivity(intent)
        }

        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_insertingDataFragment_to_loginFragment)
        }

        binding.saveBtn.setOnClickListener {
            saveEmployeeData()
        }


        return binding.root
    }

    private fun saveEmployeeData() {
        val empName = binding.EditName.text.toString()
        val empEmail = binding.EditEmail.text.toString()
        val empAgeString = binding.EditAge.text.toString()

        if (empName.trim().isEmpty()) {
            Toast.makeText(context, "Fill name please", Toast.LENGTH_SHORT).show()
            return
        }
        if (empEmail.trim().isEmpty()) {
            Toast.makeText(context, "Fill Email please", Toast.LENGTH_SHORT).show()
            return
        }
        if (empAgeString.trim().isEmpty()) {
            Toast.makeText(context, "Fill Age please", Toast.LENGTH_SHORT).show()
            return
        }

        val empAge = try {
            empAgeString.toInt()
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Invalid Age", Toast.LENGTH_SHORT).show()
            return
        }


        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        val employee = EmployeeModel(uid, empName, empEmail, empAge)

        val employeesList = mutableListOf<EmployeeModel>()
        employeesList.add(employee)

        val employeesRef = FirebaseDatabase.getInstance().getReference("Employees")
        val userEmployeeRef = employeesRef.child(uid ?: "")
        val newEmployeeRef = userEmployeeRef.push()



        for (employee in employeesList) {

            newEmployeeRef.setValue(employee)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(context, "Error: ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }

        binding.EditName.text.clear()
        binding.EditEmail.text.clear()
        binding.EditAge.text.clear()
    }
}