package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.ChatListModel

class ChatListDiffUtil : DiffUtil.ItemCallback<ChatListModel>() {
    override fun areItemsTheSame(oldItem: ChatListModel, newItem: ChatListModel): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: ChatListModel, newItem: ChatListModel): Boolean {
        return oldItem.equals(newItem)
    }

}