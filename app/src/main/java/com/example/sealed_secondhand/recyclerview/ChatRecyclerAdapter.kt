package com.example.sealed_secondhand.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.models.Chat

class ChatRecyclerAdapter(chatDiffUtil: ChatDiffUtil) : ListAdapter<Chat, ChatRecyclerAdapter.ChatViewHolder>(ChatDiffUtil()) {

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var chatTitle: TextView
        private lateinit var chatImage: ImageView
        private lateinit var lastChat: TextView
        init {
            this.chatTitle = itemView.findViewById(R.id.chatTitle)
            this.chatImage = itemView.findViewById(R.id.chatImage)
            this.lastChat = itemView.findViewById(R.id.lastChat)
        }

        fun settingView(item: Chat) {
            chatTitle.setText(item.getChatTitle())

            // TODO: 1.Firebase에서 image Root 가져오고 2.Storage 가져온 Root로 접근 3.이미지 가져오기 4.Bitmap변환 5.Set
            //chatImage.setImageBitmap()

            lastChat.setText(item.getLastChat())
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