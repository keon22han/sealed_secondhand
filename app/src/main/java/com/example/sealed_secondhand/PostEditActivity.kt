package com.example.sealed_secondhand

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sealed_secondhand.db.models.PostListModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class PostEditActivity(postListModel: PostListModel): Fragment() {

    private var postListModel: PostListModel

    init {
        this.postListModel = postListModel
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_postedit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<EditText>(R.id.editPostTitleEditText).setText(postListModel.postTitle.toString())
        view.findViewById<EditText>(R.id.editPostPriceEditText).setText(postListModel.price)
        view.findViewById<EditText>(R.id.editPostContextEditText).setText(postListModel.postContent)
        //view.findViewById<Button>(R.id.editPostUploadButton).visibility = View.GONE

        var editPostImageView = view.findViewById<ImageView>(R.id.editPostImageView)
        FirebaseStorage.getInstance().getReference(postListModel.itemImageUrl)
            .downloadUrl.addOnSuccessListener {
                if(it != null) {
                    Glide.with(this)
                        .load(it)
                        .into(editPostImageView)
                }
                else {
                    Log.i("hi", "이미지를 불러오지 못하였습니다.")
                }
            }
        view.findViewById<Button>(R.id.editPostUploadButton).setOnClickListener {
            if(view.findViewById<EditText>(R.id.editPostTitleEditText).text.toString() != "" &&
                view.findViewById<EditText>(R.id.editPostPriceEditText).text.toString() != "" &&
                view.findViewById<EditText>(R.id.editPostContextEditText).text.toString() != "") {
                //수정된 내용 데이터베이스 반영
                postListModel.postTitle = view.findViewById<EditText>(R.id.editPostTitleEditText).text.toString()
                postListModel.price = view.findViewById<EditText>(R.id.editPostPriceEditText).text.toString()
                postListModel.postContent = view.findViewById<EditText>(R.id.editPostContextEditText).text.toString()

                val dataToUpdate: MutableMap<String, Any> = HashMap()
                dataToUpdate["postTitle"] = postListModel.postTitle
                dataToUpdate["price"] = postListModel.price
                dataToUpdate["saleState"] = postListModel.saleState
                dataToUpdate["postContent"] = postListModel.postContent
                FirebaseDatabase.getInstance().getReference("Post/${postListModel.postId}").updateChildren(dataToUpdate)
                    .addOnSuccessListener {
                        Log.d("Update", "Data updated successfully");
                    }
                    .addOnFailureListener {
                        Log.i("Update", "Error updating data")
                    }
                (activity as MainActivity).popBackStack()
            }
            else
                Toast.makeText(context, "입력하지 않은 내용이 있습니다.", Toast.LENGTH_SHORT).show()
        }


        view.findViewById<Button>(R.id.editPostBackButton).setOnClickListener {
            (activity as MainActivity).popBackStack()
        }

        view.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.isSellingSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                view.findViewById<TextView>(R.id.isSellingTextView).text = "판매 중"
                postListModel.saleState = true
            }
            else {
                view.findViewById<TextView>(R.id.isSellingTextView).text = "판매 완료"
                postListModel.saleState = false
            }
        }

        view.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.isSellingSwitch).isChecked =
            postListModel.saleState
        view.findViewById<TextView>(R.id.isSellingTextView).text = if(postListModel.saleState){
            "판매 중"
        } else {
            "판매 완료"
        }
    }
}