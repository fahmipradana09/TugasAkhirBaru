package com.example.tugasakhirbaru.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tugasakhirbaru.adapter.ComponentAdapter
import com.example.tugasakhirbaru.databinding.ActivityEditIngredientBinding
import com.example.tugasakhirbaru.model.ComponentChecklist
import com.example.tugasakhirbaru.model.Menu
import com.example.tugasakhirbaru.util.ViewModelListener
import com.example.tugasakhirbaru.util.constants.IntentNameExtra.MENU_EXTRA
import com.example.tugasakhirbaru.viewmodel.DetailMenuViewModel

class EditMenuActivity : AppCompatActivity(),ViewModelListener, ComponentAdapter.Listener {
    lateinit var binding: ActivityEditIngredientBinding

    private val item by lazy {
        intent.getParcelableExtra<Menu?>(MENU_EXTRA)
    }

    private val adapter: ComponentAdapter by lazy {
        ComponentAdapter(this, this)
    }

    /*private val viewModel: DetailMenuViewModel by lazy {
        DetailMenuViewModel(database, this)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item?.let { data ->
            Log.d("DetailActivity", "Item data: $item")
            binding.item = data
            if (data.imageExists()) {
                Glide.with(this).load(data.picture).into(binding.ivDetailMakanan)
            }
            adapter.setData(data.detailIngredient)
        }

        binding.componentList.layoutManager = LinearLayoutManager(this)
        binding.componentList.adapter = adapter

        binding.addCountMenu.setOnClickListener {
            item?.let { item ->
                item.plus()
                binding.item = item
            }
        }

        binding.minesCountMenu.setOnClickListener {
            item?.let { item ->
                item.minus()
                binding.item = item
            }
        }
    }

    override fun showMessage(message: String?, isLong: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(param: String) {
        TODO("Not yet implemented")
    }

    override fun updateList(list: List<ComponentChecklist>) {
        item?.let { item ->
            item.detailIngredient.clear()
            item.detailIngredient.addAll(list)
            binding.item = item
        }
    }
}