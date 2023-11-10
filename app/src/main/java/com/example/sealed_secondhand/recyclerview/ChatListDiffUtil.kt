package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.ChatModel

class ChatListDiffUtil : DiffUtil.ItemCallback<ChatModel>() {
    override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
        return oldItem.equals(newItem)
    }

}