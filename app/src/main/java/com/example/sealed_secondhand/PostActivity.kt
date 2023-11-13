package com.example.sealed_secondhand

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.PostListModel
import com.example.sealed_secondhand.db.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class PostActivity(mainActivity: MainActivity, postListModel: PostListModel, isNewPost: Boolean): Fragment() {

    private var mainActivity: MainActivity
    private var postListModel: PostListModel
    private var isNewPost: Boolean

    private lateinit var selectImageLauncher: ActivityResultLauncher<String>
    private lateinit var postImageView: ImageView
    private lateinit var imageUploadButton: Button
    private lateinit var postTitleEditText: Any
    private lateinit var postPriceEditText: Any
    private lateinit var postContentEditText: Any
    private lateinit var postUploadButton: Button
    private lateinit var postBackButton: Button

    lateinit var postUser: User

    private lateinit var imageURI: Uri
    private lateinit var storageImagePath: String

    init {
        this.mainActivity = mainActivity
        this.postListModel = postListModel
        this.isNewPost = isNewPost
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if(isNewPost)
            inflater.inflate(R.layout.activity_post, container, false)
        else
            inflater.inflate(R.layout.activity_post1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.postImageView = view.findViewById(R.id.postImageView)
        this.imageUploadButton = view.findViewById(R.id.imageUploadButton)
        this.postTitleEditText = view.findViewById(R.id.postTitleEditText)
        this.postPriceEditText = view.findViewById(R.id.postPriceEditText)
        this.postContentEditText = view.findViewById(R.id.postContextEditText)
        this.postUploadButton = view.findViewById(R.id.postUploadButton)

        getUserData()
        if (isNewPost) {
            selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                // Handle the returned Uri
                if (uri != null) {
                    postImageView.setImageURI(uri)
                    imageURI = uri
                }
                else {
                    Toast.makeText(context, "이미지를 업로드해주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            imageUploadButton.setOnClickListener {
                selectImageLauncher.launch("image/*")
            }

            postUploadButton.setOnClickListener {
                val uuid: String = UUID.randomUUID().toString()
                storageImagePath = "/itemImage/$uuid"

                val storageReference = FirebaseStorage.getInstance().reference
                val ref = storageReference.child(storageImagePath)
                ref.putFile(imageURI)
                    .addOnSuccessListener {
                        if((postTitleEditText as EditText).text.toString() != "" && (postPriceEditText as EditText).text.toString() != "" && (postContentEditText as EditText).text.toString() != "") {

                            val dbReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Post").push()
                            val postId: String = dbReference.key!!
                            val post = PostListModel()
                            post.postTitle = (postTitleEditText as EditText).text.toString()
                            post.price = (postPriceEditText as EditText).text.toString()
                            post.postContent = (postContentEditText as EditText).text.toString()
                            post.postId = postId
                            post.itemImageUrl = storageImagePath
                            post.user = postUser

                            dbReference.setValue(post)
                        }
                        else {
                            Toast.makeText(context, "작성하지 않은 내용이 있는지 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to Upload Image File to Firebase", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        else {
            val storageReference = FirebaseStorage.getInstance().reference
            val ref = storageReference.child(postListModel.itemImageUrl)

            ref.downloadUrl.addOnSuccessListener {
                if(it != null) {
                    Glide.with(this)
                        .load(it)
                        .into(postImageView)
                }
                else {
                    Toast.makeText(context, "이미지를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            (postTitleEditText as TextView).text = postListModel.postTitle
            (postPriceEditText as TextView).text = postListModel.price
            (postContentEditText as TextView).text = postListModel.postContent

            if(postListModel.user.firebaseUserId == FirebaseAuthentication.getCurrentUser()) {
                postUploadButton.visibility = View.INVISIBLE
            }
            postUploadButton.setOnClickListener {
                mainActivity.replaceFragment(ChatActivity(postListModel.postId, postListModel.user.firebaseUserId))
            }
        }
        postBackButton = view.findViewById(R.id.postBackButton)
        postBackButton.setOnClickListener {
            mainActivity.popBackStack()
        }
    }

    private fun getUserData() {
        FirebaseDatabase.getInstance().reference.child("User")
            .addValueEventListener (object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Toast.makeText(context, FirebaseAuthentication.getCurrentUser(), Toast.LENGTH_SHORT).show()
                    for (dataSnapShot in snapshot.children) {
                        val user: User = dataSnapShot.getValue<User>() as User
                        if (user.firebaseUserId == FirebaseAuthentication.getCurrentUser()) {
                            postUser = user
                        }
                        else{

                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}