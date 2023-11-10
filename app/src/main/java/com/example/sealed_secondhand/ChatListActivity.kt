package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.ChatListModel
import com.example.sealed_secondhand.recyclerview.ChatListRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class ChatListActivity(mainActivity: MainActivity) : Fragment() {
    lateinit var mainActivity: MainActivity
    private lateinit var chatListRecyclerView: RecyclerView
    private lateinit var chatListRecyclerAdapter: ChatListRecyclerAdapter

    init {
        this.mainActivity = mainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_chatlist, container, false)
    }

    // TODO: Create 후 ChatRoom 데이터 가져와 리스트 생성, ChatListRecyclerAdapter 갱신.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // chatListRecyclerView
        chatListRecyclerView = view.findViewById(R.id.chatListRecyclerView)
        chatListRecyclerAdapter = ChatListRecyclerAdapter(mainActivity)
        chatListRecyclerView.adapter = chatListRecyclerAdapter
        chatListRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        checkChatRoom()

    }

    private fun checkChatRoom() {
        var chatList: ArrayList<ChatListModel> = ArrayList()
        // Test Code
        //FirebaseDatabase.getInstance().reference.child("chatRooms").push()
        FirebaseAuthentication.getChatListRoot()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(dataSnapshot in snapshot.children) run {
                        var chatModel = dataSnapshot.value as ChatListModel
                        chatList.add(chatModel)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        chatListRecyclerAdapter.submitList(chatList)
        chatListRecyclerAdapter.notifyDataSetChanged()
    }
}