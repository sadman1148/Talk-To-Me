package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.r3denvy.talktome.R
import com.r3denvy.talktome.databinding.FragmentSignupBinding
import com.r3denvy.talktome.model.User
import com.r3denvy.talktome.util.Constants
import com.r3denvy.talktome.util.Utils

class Signup : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private val TAG = "Signup Fragment"
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        binding.apply {
            layout.setOnClickListener(this@Signup)
            btnSignup.setOnClickListener(this@Signup)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding.layout -> {
                Log.d(TAG, "onClick() > Layout tap.")
                Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etEmail, binding.etUsername)
            }
            binding.btnSignup -> {
                Log.d(TAG, "onClick() > Signup button tap.")
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val username = binding.etUsername.text.toString()
                if (email.isBlank() && password.isBlank() && username.isBlank()) {
                    Toast.makeText(context, "Please fill out all the information", Toast.LENGTH_SHORT).show()
                } else if (email.isBlank()) {
                    Toast.makeText(context, "Please enter an Email", Toast.LENGTH_SHORT).show()
                    binding.etEmail.requestFocus()
                } else if (password.isBlank()) {
                    Toast.makeText(context, "Please enter a password", Toast.LENGTH_SHORT).show()
                    binding.etPassword.requestFocus()
                } else if (username.isBlank()) {
                    Toast.makeText(context, "Please enter a username", Toast.LENGTH_SHORT).show()
                    binding.etUsername.requestFocus()
                } else {
                    if (!Utils.isValidEmail(email)) {
                        Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                        binding.etEmail.requestFocus()
                    } else {
                        signUp(username, email, password)
                        Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etEmail, binding.etUsername)
                    }
                }
            }
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signUp() > createUserWithEmail: success")
                    addUserToDB(name, email, mAuth.currentUser?.uid!!)
                    findNavController().navigate(
                        R.id.action_signup_to_home,
                        null,
                        null,
                        FragmentNavigatorExtras(
                            binding.banner to getString(R.string.talk_to_me_baby),
                        )
                    )
                } else {
                    Log.w(TAG, "signUp() > createUserWithEmail: failure", task.exception)
                    Toast.makeText(requireContext(), "Signup failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDB(name: String, email: String, uid: String) {
        mDBRef = Firebase.database.reference
        mDBRef.child("user").child(uid).setValue(User(name,email,uid))
    }

}