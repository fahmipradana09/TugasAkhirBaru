package com.example.tugasakhirbaru.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {
    lateinit var binding : LoginActivityBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = LoginActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.tvRegisText.setOnClickListener {
            val intent = Intent (this, RegistActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.userNameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            RegisterFirebase(email,password)
        }
    }

    private fun RegisterFirebase (email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }
    }
}