package com.example.sealed_secondhand.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.sealed_secondhand.db.models.PostListModel

class PostListDiffUtil: DiffUtil.ItemCallback<PostListModel>() {
    override fun areItemsTheSame(oldItem: PostListModel, newItem: PostListModel): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: PostListModel, newItem: PostListModel): Boolean {
        return oldItem.equals(newItem)
    }
}