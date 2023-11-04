package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.db.models.Chat
import com.example.sealed_secondhand.recyclerview.ChatListRecyclerAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // chatListRecyclerView
        chatListRecyclerView = view.findViewById(R.id.chatListRecyclerView)
        chatListRecyclerAdapter = ChatListRecyclerAdapter(mainActivity)
        chatListRecyclerView.adapter = chatListRecyclerAdapter
        chatListRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        var chatList: ArrayList<Chat> = ArrayList()
        var chat1 = Chat()
        chat1.setlastChat("음 그러네요")
        chat1.setChatTitle("아이폰 15 프로 판매합니다.")
        chatList.add(chat1)
        var chat2 = Chat()
        chat2.setlastChat("감사합니다!")
        chat2.setChatTitle("냉장고 판매합니다.")
        chatList.add(chat2)
        var chat3 = Chat()
        chat3.setlastChat("넴")
        chat3.setChatTitle("도자기 판매")
        chatList.add(chat3)
        var chat4 = Chat()
        chat4.setlastChat("ㅎㅎㅎ")
        chat4.setChatTitle("운동화 판매합니다!")
        chatList.add(chat4)
        var chat5 = Chat()
        chat5.setlastChat("얼마로 내리실건가요?")
        chat5.setChatTitle("모니터암 판매합니다.")
        chatList.add(chat5)
        var chat6 = Chat()
        chat6.setlastChat("ㅠㅠㅠ 수고하세요")
        chat6.setChatTitle("갤럭시 버즈 로즈골드 판매 가격내림.")
        chatList.add(chat6)
        var chat7 = Chat()
        chat7.setlastChat("좋은거래 감사합니다~")
        chat7.setChatTitle("에어팟 프로2 판매합니다")
        chatList.add(chat7)
        var chat8 = Chat()
        chat8.setlastChat("사이즈가 어떻게 되나요?")
        chat8.setChatTitle("블레이저 판매합니다.")
        chatList.add(chat8)
        var chat9 = Chat()
        chat9.setlastChat("수고하세요~")
        chat9.setChatTitle("아이폰 12 프로 판매합니다.")
        chatList.add(chat9)
        var chat10 = Chat()
        chat10.setlastChat(" 감사해요 ㅠㅠ ")
        chat10.setChatTitle("주방세척기구 나눔합니다")
        chatList.add(chat10)
        var chat11 = Chat()
        chat11.setlastChat("수고하세요~")
        chat11.setChatTitle("선글라스 판매")
        chatList.add(chat11)
        chatListRecyclerAdapter.submitList(chatList)
        chatListRecyclerAdapter.notifyDataSetChanged()
    // TODO: View 접근하여 ChatRecyclerAdapter 갱신 코드 작성 및 View 이벤트 할당
    }
}