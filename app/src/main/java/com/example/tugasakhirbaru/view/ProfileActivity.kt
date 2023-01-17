package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.ActivityProfileBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.KotlinExt.openEditProfileActivity
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.example.tugasakhirbaru.util.constants.IntentNameExtra
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    private val user: ArrayList<Users> = arrayListOf()

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        showProfile()

        binding.btnEditProfil.setOnClickListener {
            openEditProfileActivity(
            )
        }

    }

    fun showProfile(){
        val currectUser = FirebaseAuth.getInstance().currentUser
        val userId = currectUser?.uid
        val userRef = database.child(userId!!)
        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val username = snapshot.child("username").value.toString()
                        val password = snapshot.child("password").value.toString()
                        val alamat = snapshot.child("alamat").value.toString()
                        val phone = snapshot.child("phone").value.toString()
                        val user = Users(username = username, password = password, alamat = alamat, phone = phone)
                        Log.d("tes", "user : $user")
                        binding.item = user
                        }
                    }
                }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        }



    }


