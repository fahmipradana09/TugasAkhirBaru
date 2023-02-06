package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.databinding.ActivityEditProfileBinding
import com.example.tugasakhirbaru.model.Users
import com.example.tugasakhirbaru.repository.UserPreference
import com.example.tugasakhirbaru.util.KotlinExt.openProfileActivity
import com.example.tugasakhirbaru.util.ViewModelListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditProfileActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityEditProfileBinding
    private val userPreference:UserPreference by lazy{
        UserPreference.getInstance(this)
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("users")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showProfile()

        binding.btnEditProfil.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val phone = binding.etPhone.text.toString()
            userUpdate(username, email, alamat, phone)
            finish()
        }

    }

    override fun showMessage(message: String?, isLong: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(param: String) {
        openProfileActivity()
        finish()
    }

    fun showProfile() {
        val currectUser = FirebaseAuth.getInstance().currentUser
        val userId = currectUser?.uid
        val userRef = database.child(userId!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val email = snapshot.child("email").value.toString()
                        val username = snapshot.child("username").value.toString()
                        val password = snapshot.child("password").value.toString()
                        val alamat = snapshot.child("alamat").value.toString()
                        val phone = snapshot.child("phone").value.toString()
                        val user = Users(email= email, username = username, password = password, alamat = alamat, phone = phone)
                        Log.d("tes", "user : $user")
                        userPreference.saveUser(user!!)
                        binding.item = user
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun userUpdate(username: String, email: String, alamat: String, phone: String) {
        val userId = auth.currentUser?.uid
        val user = mapOf<String, String>(
            "email" to email,
            "username" to username,
            "alamat" to alamat,
            "phone" to phone
        )
        if (!userId.isNullOrBlank()) {
            database.child(userId).updateChildren(user).addOnSuccessListener {
                Toast.makeText(this, "akun telah diubah", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {

                Toast.makeText(this, "akun telah diubah", Toast.LENGTH_SHORT).show()

            }
        }
    }


}