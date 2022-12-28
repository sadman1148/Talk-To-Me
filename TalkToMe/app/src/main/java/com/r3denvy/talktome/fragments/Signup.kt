package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import com.r3denvy.talktome.R
import com.r3denvy.talktome.databinding.FragmentSignupBinding
import com.r3denvy.talktome.util.Utils

class Signup : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private val TAG = "Signup Fragment"
    private lateinit var mAuth: FirebaseAuth

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
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding.layout -> {
                Log.d(TAG, "onClick() > Layout tap.")
                Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etEmail)
            }
            binding.btnSignup -> {
                signUp(binding.etEmail.text.toString(), binding.etPassword.text.toString())
            }
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signUp() > createUserWithEmail: success")
                    findNavController().navigate(R.id.action_login_to_home)
                } else {
                    Log.w(TAG, "signUp() > createUserWithEmail: failure", task.exception)
                    Toast.makeText(requireContext(), "Signup failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}