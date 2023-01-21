package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.databinding.ActivityDetailMenuBinding
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.KotlinExt.openEditIngredient
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA
import com.example.tugasakhirbaru.viewmodel.DetailMenuViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailMenuActivity : AppCompatActivity(), ViewModelListener {
    lateinit var binding: ActivityDetailMenuBinding

    private val item by lazy {
        intent.getParcelableExtra<Menu?>(MENU_EXTRA)
    }

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("component")
    }

    private val viewModel: DetailMenuViewModel by lazy {
        DetailMenuViewModel(database, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel

        Log.d("DetailActivity", "Item data: $item")

        item?.let { data ->
            viewModel.item = data
            if (data.imageExists()) {
                Glide.with(this).load(data.picture).into(binding.ivDetailMakanan)
            }
        }

        viewModel.getIngredient()
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        Toast.makeText(this, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    override fun navigateTo(param: String) {
        if (param == DetailMenuViewModel.OPEN_EDIT) {
            openEditIngredient(viewModel.item)
        }
    }
}