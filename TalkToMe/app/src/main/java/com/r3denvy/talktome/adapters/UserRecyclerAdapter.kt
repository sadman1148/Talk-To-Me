package com.r3denvy.talktome.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.r3denvy.talktome.R
import com.r3denvy.talktome.model.User

class UserRecyclerAdapter(val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_frame, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.name
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name",currentUser.name)
            bundle.putString("uid",currentUser.uid)
            bundle.putString("email",currentUser.email)
            it.findNavController().navigate(R.id.action_home_to_chat, bundle)
        }
    }

    override fun getItemCount(): Int = userList.size

}