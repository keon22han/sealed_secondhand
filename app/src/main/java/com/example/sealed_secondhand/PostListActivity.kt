package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.db.models.PostListModel
import com.example.sealed_secondhand.recyclerview.PostListRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class PostListActivity(mainActivity: MainActivity): Fragment() {
    var mainActivity: MainActivity
    private lateinit  var postListRecyclerView: RecyclerView
    private lateinit var postListRecyclerAdapter: PostListRecyclerAdapter

    init {
        // mainActivity를 가져오는 이유는 Adapter에 사용되는 View 클릭 시 mainActivity에서 Fragment 변경이 필요하기 때문.
        this.mainActivity = mainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_postlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //postListRecyclerView
        postListRecyclerView = view.findViewById(R.id.postListRecyclerView)
        postListRecyclerAdapter = PostListRecyclerAdapter(mainActivity)
        //adapter 설정
        postListRecyclerView.adapter = postListRecyclerAdapter
        //layout Manager 설정
        postListRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        //FirebaseDatabase로부터 PostListModel 데이터들을 전부 불러옴.
        // TODO: 게시글 필터 기능을 이용하기 위해서 하나의 Flag(ex: salesOn)를 true로 하여 판매중인 상품만 리스트도 가능함.
        // TODO: checkPostList()에서 onDataChange 안에 각 dataSnapShot의 SaleState가 true인지 false인지 확인하면 됨.
        checkPostList()
    }

    private fun checkPostList() {
        var postList: ArrayList<PostListModel> = ArrayList()
        FirebaseDatabase.getInstance().reference.child("Post")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    postList.clear()
                    for (dataSnapShot in snapshot.children) {
                        postList.add(dataSnapShot.getValue<PostListModel>() as PostListModel)
                    }

                    // 전체 데이터를 불러왔으니 Adapter에게 알리기.
                    postListRecyclerAdapter.submitList(postList)
                    postListRecyclerAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}