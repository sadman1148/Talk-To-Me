package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionInflater
import com.r3denvy.talktome.databinding.FragmentSignupBinding
import com.r3denvy.talktome.util.Utils

class Signup : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSignupBinding
    private val TAG = "Signup Fragment"

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
                Utils.clearFocusAndHideKeyboard(
                    this,
                    binding.etPassword,
                    binding.etUsername,
                    binding.etEmail
                )
            }
        }
    }

}