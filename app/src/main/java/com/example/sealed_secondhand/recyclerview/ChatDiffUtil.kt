package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.ChatModel

class ChatDiffUtil: DiffUtil.ItemCallback<ChatModel.Comment>() {
    override fun areItemsTheSame(oldItem: ChatModel.Comment, newItem: ChatModel.Comment): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: ChatModel.Comment, newItem: ChatModel.Comment): Boolean {
        return oldItem.equals(newItem)
    }

}