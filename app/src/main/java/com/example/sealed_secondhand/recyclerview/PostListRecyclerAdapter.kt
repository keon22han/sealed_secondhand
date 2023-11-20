package com.example.sealed_secondhand.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sealed_secondhand.MainActivity
import com.example.sealed_secondhand.PostActivity
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.ChatModel
import com.example.sealed_secondhand.db.models.PostListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.awt.font.TextAttribute

class PostListRecyclerAdapter(mainActivity: MainActivity) : ListAdapter<PostListModel, PostListRecyclerAdapter.PostListViewHolder>(PostListDiffUtil()) {

    private var mainActivity: MainActivity
    init {
        this.mainActivity = mainActivity
    }

    class PostListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // PostLsit item에 들어가는 View들.
        // TODO: 게시글 작성 (진우)가 ImageView Storage에 저장하는 코드 작성하고, 게시글 올리면서 데이터 저장 시 해당 물품
        // TODO: 이미지의 URL을 받아 저장해줄거임. 추후 그 값은 가져오도록 해주어야 함.
        var postListImageView: ImageView
        var postListTitleTextView: TextView
        var postListPriceTextView: TextView
        var postListSaleStateTextView: TextView
        var postListLayout: LinearLayout
        var postListUserName: TextView

        init {
            this.postListImageView = itemView.findViewById(R.id.postListImageView)
            this.postListTitleTextView = itemView.findViewById(R.id.postListTitleTextView)
            this.postListPriceTextView = itemView.findViewById(R.id.postListPriceTextView)
            this.postListSaleStateTextView = itemView.findViewById(R.id.postListSaleStateTextView)
            this.postListLayout = itemView.findViewById(R.id.postListLayout)
            this.postListUserName = itemView.findViewById(R.id.postListUserName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.postlist_item, parent, false)
        return PostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.postListTitleTextView.text = getItem(position).postTitle
        holder.postListPriceTextView.text = getItem(position).price
        val storageReference = FirebaseStorage.getInstance().reference
        val ref = storageReference.child(getItem(position).itemImageUrl)
        ref.downloadUrl.addOnSuccessListener {
            if(it != null) {
                Glide.with(holder.itemView.context)
                    .load(it)
                    .into(holder.postListImageView)
            }
            else {
                Log.i("hi", "이미지를 불러오지 못하였습니다.")
            }
        }

        if(getItem(position).saleState) {
            holder.postListSaleStateTextView.text  = "판매중"
        }
        else {
            holder.postListSaleStateTextView.text = "판매 완료"
        }

        holder.postListLayout.setOnClickListener {
            // TODO: PostActivity 추가되면 해당 코드 주석 제거
            mainActivity.replaceFragment(PostActivity(mainActivity, getItem(position).itemImageUrl, getItem(position), false))
        }

        holder.postListUserName.setText(getItem(position).user.nickName)
    }
}