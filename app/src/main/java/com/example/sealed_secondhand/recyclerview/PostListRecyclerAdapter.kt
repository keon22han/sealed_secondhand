package com.example.sealed_secondhand.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sealed_secondhand.MainActivity
import com.example.sealed_secondhand.R
import com.example.sealed_secondhand.db.models.PostListModel

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

        init {
            this.postListImageView = itemView.findViewById(R.id.postListImageView)
            this.postListTitleTextView = itemView.findViewById(R.id.postListTitleTextView)
            this.postListPriceTextView = itemView.findViewById(R.id.postListPriceTextView)
            this.postListSaleStateTextView = itemView.findViewById(R.id.postListSaleStateTextView)
            this.postListLayout = itemView.findViewById(R.id.postListLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.postlist_item, parent, false)
        return PostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.postListTitleTextView.text = getItem(position).postTitle
        holder.postListPriceTextView.text = getItem(position).price

        if(getItem(position).saleState) {
            holder.postListSaleStateTextView.text  = "판매중"
        }
        else {
            holder.postListSaleStateTextView.text = "판매 완료"
        }

        holder.postListLayout.setOnClickListener {
            // TODO: PostActivity 추가되면 해당 코드 주석 제거
            // mainActivity.replaceFragment(PostActivity(getItem(position))
        }
    }
}