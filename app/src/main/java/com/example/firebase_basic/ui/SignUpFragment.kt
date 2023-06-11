package com.example.firebase_basic.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebase_basic.R
import com.example.firebase_basic.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.SignUpbtn.setOnClickListener {
            val email = binding.emailEt.text.toString();
            val password = binding.passET.text.toString();
            val confirmPass = binding.confirmPassEt.text.toString();

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (isPasswordValid(password) && password == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                            } else {

                                binding.emailEt.setText("")
                                binding.passET.setText("")
                                binding.confirmPassEt.setText("")

                                Toast.makeText(
                                    context,
                                    it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Password is not valid or is not matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    context,
                    "Please fill out the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }


    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
        return password.matches(passwordRegex)
    }


}