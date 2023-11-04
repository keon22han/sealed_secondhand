package com.example.sealed_secondhand.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.models.ChatLog

class ChatRecyclerAdapter : ListAdapter<ChatLog, ChatRecyclerAdapter.ChatViewHolder>(ChatDiffUtil()) {

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private lateinit var contentTextView: TextView

        init{
            contentTextView = itemView.findViewById(R.id.chatTextView)
        }

        fun settingView(item: ChatLog) {
            contentTextView.setText(item.getContent())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        var view: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.chat_item, parent, false)
        var chatViewHolder = ChatViewHolder(view)
        return chatViewHolder
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.settingView(getItem(position))
    }
}