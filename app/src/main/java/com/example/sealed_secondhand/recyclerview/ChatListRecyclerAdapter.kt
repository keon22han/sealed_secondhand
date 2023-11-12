package com.example.sealed_secondhand.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.ChatActivity
import com.example.sealed_secondhand.MainActivity
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.models.ChatModel

class ChatListRecyclerAdapter(mainActivity: MainActivity) : ListAdapter<ChatModel, ChatListRecyclerAdapter.ChatListViewHolder>(ChatListDiffUtil()) {

    private var mainActivity: MainActivity

    init {
        this.mainActivity = mainActivity
    }

    class ChatListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var lastChat: TextView
        private var chatTitle: TextView
        private var chatImage: ImageView
        private var layout: LinearLayout
        private lateinit var destUid: String

        init {
            this.chatTitle = itemView.findViewById(R.id.chatTitle)
            this.chatImage = itemView.findViewById(R.id.chatImage)
            this.lastChat = itemView.findViewById(R.id.lastChat)
            this.layout = itemView.findViewById(R.id.chatListContent)
        }

        fun getLayout(): LinearLayout {
            return layout
        }

        fun settingView(item: ChatModel) {
            chatTitle.text = item.chatTitle
            lastChat.text = item.lastChat
            destUid = item.destUid

            // TODO: 1.Firebase에서 image Root 가져오고 2.Storage 가져온 Root로 접근 3.이미지 가져오기 4.Bitmap변환 5.Set
            //chatImage.setImageBitmap()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.chatlist_item, parent, false)
        return ChatListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.settingView(getItem(position))
        holder.getLayout().setOnClickListener {
            mainActivity.replaceFragment(ChatActivity(getItem(position).chatTitle, getItem(position).postId, getItem(position).destUid))
        }
    }
}