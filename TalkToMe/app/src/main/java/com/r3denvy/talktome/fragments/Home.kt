package com.r3denvy.talktome.fragments

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.r3denvy.talktome.R
import com.r3denvy.talktome.adapters.UserRecyclerAdapter
import com.r3denvy.talktome.databinding.FragmentHomeBinding
import com.r3denvy.talktome.model.User


class Home : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var userRecyclerAdapter: UserRecyclerAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private val TAG: String = "Home Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getPreferences(MODE_PRIVATE)
        userList = ArrayList()
        userRecyclerAdapter = UserRecyclerAdapter(requireContext(), userList)
        mAuth = FirebaseAuth.getInstance()
        mDbRef = Firebase.database.reference
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currentUser?.uid) {
                        Log.d(TAG, "User: ${currentUser?.name} added to list.")
                        userList.add(currentUser!!)
                    } else {
                        sharedPreferences.edit().putString(getString(R.string.LOGGED_USERNAME_KEY), currentUser?.name.toString()).apply()
                    }
                }
                userRecyclerAdapter.notifyDataSetChanged()
                if (userList.size == 0) {
                    binding.emptyBox.visibility = View.VISIBLE
                    binding.emptyBoxText.visibility = View.VISIBLE
                } else {
                    binding.emptyBox.visibility = View.GONE
                    binding.emptyBoxText.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "Data read failed: ${error.code}")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.ivLogout.setOnClickListener(this@Home)
        binding.rvUsers.adapter = userRecyclerAdapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.tvUsername.text = sharedPreferences.getString(getString(R.string.LOGGED_USERNAME_KEY),",Talk To Me")
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivLogout -> {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.Logout))
                    .setMessage(getString(R.string.alert_logout))
                    .setPositiveButton(
                        getString(R.string.yes),
                        DialogInterface.OnClickListener { _, _ ->
                            mAuth.signOut()
                            findNavController().navigate(R.id.action_home_to_login)
                            sharedPreferences.edit().clear().apply()
                        })
                    .setNegativeButton(getString(R.string.no), null)
                    .setIcon(R.drawable.ic_logout)
                    .show()
            }
        }
    }

}