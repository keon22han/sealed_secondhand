package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.Chat

class ChatListDiffUtil : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return oldItem.equals(newItem)
    }

}