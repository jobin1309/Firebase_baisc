package com.example.firebase_basic.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebase_basic.R
import com.example.firebase_basic.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding;
    private lateinit var firebaseAuth: FirebaseAuth;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupbtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.loginbtn.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.editEmail.setText("")
                            binding.editPassword.setText("")
                            findNavController().navigate(R.id.action_loginFragment_to_insertingDataFragment)
                        } else {
                            Log.d("Login btn", "Crashed")
                            Toast.makeText(
                                context,
                                "Password or email not recognized",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    context,
                    "Please input email and password",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        return binding.root
    }
}




