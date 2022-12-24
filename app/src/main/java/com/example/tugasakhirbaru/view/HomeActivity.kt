package com.example.tugasakhirbaru.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}