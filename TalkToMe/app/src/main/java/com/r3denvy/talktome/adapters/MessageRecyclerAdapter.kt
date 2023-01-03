package com.r3denvy.talktome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.r3denvy.talktome.R
import com.r3denvy.talktome.model.Message

class MessageRecyclerAdapter(val context: Context, private val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val received = 1
    private val sent = 0

    override fun getItemViewType(position: Int): Int =
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(messageList[position].senderId)) sent else received

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.tv_sent)
        val sentTime: TextView = itemView.findViewById(R.id.time_sent)
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessage: TextView = itemView.findViewById(R.id.tv_received)
        val receivedTime: TextView = itemView.findViewById(R.id.time_received)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0) (MessageRecyclerAdapter.SentViewHolder(
            LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
        )) else (MessageRecyclerAdapter.ReceivedViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received, parent, false)
        ))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java) {
            (holder as SentViewHolder).apply {
                sentMessage.text = currentMessage.message
                sentTime.text = currentMessage.time
            }
        } else {
            (holder as ReceivedViewHolder).apply {
                receivedMessage.text = currentMessage.message
                receivedTime.text = currentMessage.time
            }
        }
    }

    override fun getItemCount(): Int = messageList.size

}