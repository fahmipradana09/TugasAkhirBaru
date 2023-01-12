package com.example.tugasakhirbaru.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.ActivityProfileBinding
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.util.KotlinExt.openEditProfileActivity
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.example.tugasakhirbaru.util.constants.IntentNameExtra
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
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
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val listData = arrayListOf<Users>()
                    for(userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(Users::class.java)
                        if (user != null){
                            binding.item = user
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


}