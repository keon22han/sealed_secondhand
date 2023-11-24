package com.example.sealed_secondhand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

    private lateinit var filteringAllButton: Button
    private lateinit var filteringSalesButton: Button
    private lateinit var filteringSaledButton: Button

    init {
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

        // 필터링을 위한 clicklistener 작성
        filteringAllButton = view.findViewById(R.id.showAllProduct)
        filteringSalesButton = view.findViewById(R.id.showSalesProduct)
        filteringSaledButton = view.findViewById(R.id.showSaledProduct)

        filteringAllButton.setOnClickListener {
            checkPostList(null)
        }

        filteringSalesButton.setOnClickListener {
            checkPostList(true)
        }

        filteringSaledButton.setOnClickListener {
            checkPostList(false)
        }

        checkPostList(null)
    }

    fun checkPostList(filterFlag : Boolean? = null) {
        var postList: ArrayList<PostListModel> = ArrayList()
        var postListModel : PostListModel

        FirebaseDatabase.getInstance().reference.child("Post")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    postList.clear()
                    for (dataSnapShot in snapshot.children) {
                        postListModel = dataSnapShot.getValue<PostListModel>() as PostListModel
                        if (postListModel.saleState == filterFlag ?: postListModel.saleState) {
                            postList.add(postListModel)
                        }
                    }

                    postListRecyclerAdapter.submitList(postList)
                    postListRecyclerAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}