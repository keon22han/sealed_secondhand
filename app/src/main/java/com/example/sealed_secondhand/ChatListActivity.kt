package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.ChatModel
import com.example.sealed_secondhand.recyclerview.ChatListRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class ChatListActivity(mainActivity: MainActivity) : Fragment() {
    var mainActivity: MainActivity
    private lateinit var chatListRecyclerView: RecyclerView
    private lateinit var chatListRecyclerAdapter: ChatListRecyclerAdapter

    init {
        this.mainActivity = mainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_chatlist, container, false)
    }

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
        var chatList: ArrayList<ChatModel> = ArrayList()
        FirebaseAuthentication.getChatListRoot()
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(dataSnapshot in snapshot.children) {
                        var chatRoom: ChatModel = dataSnapshot.getValue<ChatModel>() as ChatModel
                        if ((chatRoom.destUid == FirebaseAuthentication.getCurrentUser()) || (chatRoom.myUid == FirebaseAuthentication.getCurrentUser())) {
                            chatList.add(chatRoom)
                        }
                    }

                    chatListRecyclerAdapter.submitList(chatList)
                    chatListRecyclerAdapter.notifyDataSetChanged()
                    chatListRecyclerView.scrollToPosition(0)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}