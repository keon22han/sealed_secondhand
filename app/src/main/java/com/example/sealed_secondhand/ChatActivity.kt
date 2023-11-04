package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.db.models.Chat
import com.example.sealed_secondhand.db.models.ChatLog
import com.example.sealed_secondhand.recyclerview.ChatRecyclerAdapter

class ChatActivity(chatTitle: String) : Fragment() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    private lateinit var chatTitle: String

    init {
        this.chatTitle = chatTitle
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // chatListRecyclerView
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        chatRecyclerAdapter = ChatRecyclerAdapter()
        chatRecyclerView.adapter = chatRecyclerAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)


        var chatLogList: ArrayList<ChatLog> = ArrayList()
        view.findViewById<Button>(R.id.sendButton).setOnClickListener {
            chatLogList.add(ChatLog(view.findViewById<EditText>(R.id.chatEditText).text.toString()))
            chatRecyclerAdapter.submitList(chatLogList)
            chatRecyclerAdapter.notifyDataSetChanged()
        }

        // TODO: View 접근하여 ChatRecyclerAdapter 갱신 코드 작성 및 View 이벤트 할당
    }
}