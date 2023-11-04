package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.ChatLog

class ChatDiffUtil: DiffUtil.ItemCallback<ChatLog>() {
    override fun areItemsTheSame(oldItem: ChatLog, newItem: ChatLog): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: ChatLog, newItem: ChatLog): Boolean {
        return oldItem.equals(newItem)
    }

}