package com.r3denvy.talktome.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.r3denvy.talktome.R
import com.r3denvy.talktome.adapters.UserRecyclerAdapter
import com.r3denvy.talktome.databinding.FragmentHomeBinding
import com.r3denvy.talktome.model.User

class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var userRecyclerAdapter: UserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userList = ArrayList()
    }

    override fun onResume() {
        super.onResume()
        if (userList.size == 0) {
            binding.emptyBox.visibility = View.VISIBLE
            binding.emptyBoxText.visibility = View.VISIBLE
        } else {
            binding.emptyBox.visibility = View.GONE
            binding.emptyBoxText.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

}