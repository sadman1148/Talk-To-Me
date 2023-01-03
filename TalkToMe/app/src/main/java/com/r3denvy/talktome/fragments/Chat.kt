package com.r3denvy.talktome.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.r3denvy.talktome.R
import com.r3denvy.talktome.adapters.MessageRecyclerAdapter
import com.r3denvy.talktome.databinding.FragmentChatBinding
import com.r3denvy.talktome.model.Message
import com.r3denvy.talktome.util.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Chat : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentChatBinding
    private lateinit var messageList: ArrayList<Message>
    private lateinit var messageRecyclerAdapter: MessageRecyclerAdapter
    private lateinit var mDbRef: DatabaseReference
    private var senderUid: String? = null
    private var receiverUid: String? = null

    var receiverRoom: String? = null
    var senderRoom: String? = null

    private val TAG: String = "Chat Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        messageList = ArrayList()
        messageRecyclerAdapter = MessageRecyclerAdapter(requireContext(), messageList)
        receiverUid = arguments?.getString("uid")
        senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        mDbRef = Firebase.database.reference
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageRecyclerAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "Message read error: ${error.message}")
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        binding.chatBuddyName.text = arguments?.getString("name")
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChat.adapter = messageRecyclerAdapter
        binding.apply {
            ivSendText.setOnClickListener(this@Chat)
        }
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.ivSendText -> {
                if (binding.etSendText.text.isBlank()) {
                    Toast.makeText(requireContext(), getString(R.string.blank_chatbox_toast_text), Toast.LENGTH_SHORT).show()
                } else {
                    val messageObj = Message(binding.etSendText.text.toString(), senderUid!!, Utils.getTime())
                    binding.etSendText.text.clear()
                    mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                        .setValue(messageObj).addOnSuccessListener {
                            mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                                .setValue(messageObj)
                        }
                }
            }
        }
    }
}