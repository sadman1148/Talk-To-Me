package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.r3denvy.talktome.R
import com.r3denvy.talktome.databinding.FragmentLoginBinding
import com.r3denvy.talktome.util.Utils


class Login : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private val TAG = "Login Fragment"

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
                Utils.clearFocusAndHideKeyboard(this, binding.etPassword, binding.etUsername, null)
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
                        binding.linearLayoutCompat to getString(R.string.linearLayoutCompat),
                        binding.etUsername to getString(R.string.username),
                        binding.etPassword to getString(R.string.password)
                    )
                )
            }
        }
    }

}