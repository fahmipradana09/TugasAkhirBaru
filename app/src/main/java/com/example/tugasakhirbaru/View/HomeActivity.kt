package com.example.tugasakhirbaru.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.databinding.HomeActivityBinding
import com.example.tugasakhirbaru.databinding.LoginActivityBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding : HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = HomeActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}