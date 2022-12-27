package com.r3denvy.talktome.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.r3denvy.talktome.R
import com.r3denvy.talktome.databinding.FragmentLoginBinding

class Login : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().popBackStack()
        binding.apply {
            layout.setOnClickListener(this@Login)
        }
    }

    override fun onClick(view: View) {
        when (view) {
            binding.layout -> {
                hideKeyboard()
                binding.etPassword.clearFocus()
                binding.etUsername.clearFocus()
            }
            binding.btnSignup -> findNavController().navigate(R.id.action_login_to_signup)
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}