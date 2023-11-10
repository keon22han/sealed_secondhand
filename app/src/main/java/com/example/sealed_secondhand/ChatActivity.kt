package com.example.sealed_secondhand

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.ChatModel
import com.example.sealed_secondhand.recyclerview.ChatRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener

class ChatActivity(chatTitle: String, chatRoomUid: String) : Fragment() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    private lateinit var sendButton : Button
    private var firebaseDatabase: FirebaseDatabase
    private var chatRoomUid: String
    private lateinit var chatTitle: String
    private var comments: ArrayList<ChatModel.Comment> = ArrayList()

    init {
        this.chatTitle = chatTitle
        this.chatRoomUid = chatRoomUid
        this.firebaseDatabase = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendButton = view.findViewById(R.id.sendButton)
        sendButton.setOnClickListener {
            sendMessageToDataBase(view.findViewById(R.id.chatEditText))
        }
        // chatListRecyclerView
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        chatRecyclerAdapter = ChatRecyclerAdapter()
        chatRecyclerView.adapter = chatRecyclerAdapter
        chatRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        //채팅내용 읽어들임
        FirebaseAuthentication.getMessageRoot(chatRoomUid).addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments.clear()

                for (dataSnapshot in snapshot.children) {
                    //Log.i("log로그", dataSnapshot.value.)
                    comments.add(dataSnapshot.value as ChatModel.Comment)
                }
                chatRecyclerAdapter.submitList(comments)
                chatRecyclerAdapter.notifyDataSetChanged()

                chatRecyclerView.scrollToPosition(comments.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to get chatting log", Toast.LENGTH_SHORT).show()
            }
        })


        // TODO: View 접근하여 ChatRecyclerAdapter 갱신 코드 작성 및 View 이벤트 할당
    }

    private fun sendMessageToDataBase(editText: EditText) {
        var message = editText.text.toString()
        var comment: ChatModel.Comment = ChatModel.Comment()
        comment.uid = FirebaseAuthentication.getCurrentUser()
        comment.message = message
        comment.timestamp = ServerValue.TIMESTAMP
        FirebaseAuthentication.sendMessageToDataBase(chatRoomUid, comment)
            .addOnSuccessListener{
                editText.setText("")
            }
            .addOnFailureListener{
                Toast.makeText(context, "Failed to send message", Toast.LENGTH_SHORT)
            }
    }


}