package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import com.r3denvy.talktome.R
import com.r3denvy.talktome.databinding.FragmentLoginBinding
import com.r3denvy.talktome.util.Utils


class Login : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val TAG = "Login Fragment"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            layout.setOnClickListener(this@Login)
            btnLogin.setOnClickListener(this@Login)
            btnSignup.setOnClickListener(this@Login)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding.layout -> {
                Log.d(TAG, "onClick() > Layout tap.")
                Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etEmail, null)
            }
            binding.btnSignup -> {
                Log.d(TAG, "onClick() > SignUp button tap.")
                findNavController().navigate(
                    R.id.action_login_to_signup,
                    null,
                    null,
                    FragmentNavigatorExtras(
                        binding.btnSignup to getString(R.string.login),
                        binding.banner to getString(R.string.talk_to_me_baby),
                        binding.etPassword to getString(R.string.password),
                        binding.etEmail to getString(R.string.email),
                        binding.linearLayoutCompat to getString(R.string.linearLayoutCompat)
                    )
                )
                binding.etEmail.text.clear()
                binding.etPassword.text.clear()
            }
            binding.btnLogin -> {
                login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etEmail, null)
            }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signUp() > createUserWithEmail: success")
                    findNavController().navigate(R.id.action_login_to_home)
                } else {
                    Log.w(TAG, "signUp() > createUserWithEmail: failure", task.exception)
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}