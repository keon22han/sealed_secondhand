package com.example.sealed_secondhand.recyclerview

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.ChatModel
import com.example.sealed_secondhand.db.models.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class ChatRecyclerAdapter(comments: ArrayList<ChatModel.Comment>) : ListAdapter<ChatModel.Comment, ChatRecyclerAdapter.ChatViewHolder>(ChatDiffUtil()) {
    private var simpleDateFormat = SimpleDateFormat("yyy.MM.dd HH:mm")
    private var comments: ArrayList<ChatModel.Comment> = ArrayList()

    init {
        this.comments = comments
    }
    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var chatView: View

        init{
            chatView = itemView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if(comments[position].uid == FirebaseAuthentication.getCurrentUser()) {
            holder.chatView.findViewById<TextView>(R.id.chatTextView).text = comments[position].message
            holder.chatView.findViewById<LinearLayout>(R.id.chatTextLayout).setBackgroundResource(R.drawable.button_style2)
            holder.chatView.findViewById<TextView>(R.id.timeTextView).gravity = Gravity.LEFT
            holder.chatView.findViewById<LinearLayout>(R.id.chatLayout).gravity = Gravity.RIGHT
        }
        else {
//            Glide.with(holder.itemView.context)
//                .load(destUser.profileImgUrl)
//                .apply(RequestOptions().circleCrop())
//                .into(holder.chatView.findViewById(R.id.userProfileImageView))

            holder.chatView.findViewById<TextView>(R.id.chatTextView).text = comments[position].message
            holder.chatView.findViewById<LinearLayout>(R.id.chatTextLayout).setBackgroundResource(R.drawable.button_style3)
            holder.chatView.findViewById<LinearLayout>(R.id.chatLayout).gravity = Gravity.LEFT
            holder.chatView.findViewById<TextView>(R.id.timeTextView).gravity = Gravity.RIGHT
        }
        holder.chatView.findViewById<TextView>(R.id.timeTextView).text = getDateTime(position)
    }

    private fun getDateTime(position: Int): String? {
        val unixTime = getItem(position).timestamp as Long
        val date = Date(unixTime)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        return simpleDateFormat.format(date)
    }
}