package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tugasakhirbaru.databinding.ActivityDetailBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    private val item by lazy {
        intent.getParcelableExtra<Menu?>(MENU_EXTRA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("DetailActivity", "Item data: $item")
        // binding.item = item
    }
}